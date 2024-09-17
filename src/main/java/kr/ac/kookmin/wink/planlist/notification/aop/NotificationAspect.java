package kr.ac.kookmin.wink.planlist.notification.aop;

import kr.ac.kookmin.wink.planlist.friend.domain.FriendStatus;
import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import kr.ac.kookmin.wink.planlist.friend.service.FriendshipService;
import kr.ac.kookmin.wink.planlist.notification.domain.Notification;
import kr.ac.kookmin.wink.planlist.notification.domain.NotificationMessage;
import kr.ac.kookmin.wink.planlist.notification.service.NotificationService;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.UserSharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.schedule.domain.UserSharedSchedule;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.dto.response.RegisterResponseDTO;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class NotificationAspect {
    private final NotificationService notificationService;
    private final UserService userService;
    private final FriendshipService friendshipService;

    @AfterReturning(value = "@annotation(kr.ac.kookmin.wink.planlist.notification.aop.Notify)", returning = "registerResponseDTO")
    public void notifyWelcome(JoinPoint joinPoint, RegisterResponseDTO registerResponseDTO) {
        Notify notify = getAnnotation(joinPoint);
        NotificationMessage message = notify.value();
        Long userId = registerResponseDTO.getUser().getId();
        User user = userService.findUserById(userId);

        notificationService.sendNotification(user, message, userId);
    }

    @AfterReturning(value = "@annotation(kr.ac.kookmin.wink.planlist.notification.aop.Notify)", returning = "friendship")
    public void notifyFriend(JoinPoint joinPoint, Friendship friendship) {
        Notify notify = getAnnotation(joinPoint);
        NotificationMessage message = notify.value();

        if (message == NotificationMessage.FRIEND_REQUEST) {
            notificationService.sendNotification(friendship.getFollowing(), message, friendship.getId());
        } else if (message == NotificationMessage.FRIEND_ACCEPTED) {
            notificationService.handleFriendAccepted(friendship);
        }
    }

    @Pointcut("execution(* kr.ac.kookmin.wink.planlist.friend.service.FriendshipService.delete(..)) && args(friendshipId)")
    public void friendshipDeletePointcut(Long friendshipId) {}

    @Before("friendshipDeletePointcut(friendshipId)")
    public void handleFriendshipDelete(JoinPoint joinPoint, Long friendshipId) {
        Friendship friendship = friendshipService.findById(friendshipId);

        // 친구 관계 상태가 WAITING일 때만 알림 삭제 로직 수행
        if (friendship.getStatus() == FriendStatus.WAITING) {
            // 알림 조회
            Notification notification = notificationService.findByMessageType(
                    NotificationMessage.FRIEND_REQUEST,
                    friendshipId,
                    friendship.getFollowing()
            );

            // 알림 삭제
            notificationService.deleteNotification(notification);
        }
    }

    @Pointcut("execution(* kr.ac.kookmin.wink.planlist.shared.calendar.repository.UserSharedCalendarRepository.delete(..)) && args(userSharedCalendar)")
    public void calendarRejectPointcut(UserSharedCalendar userSharedCalendar) {}

    @Before("calendarRejectPointcut(userSharedCalendar)")
    public void handleCalendarRejectNotification(JoinPoint joinPoint, UserSharedCalendar userSharedCalendar) {
        // 삭제 전에 알림 처리
        if (userSharedCalendar.isInvitationStatus()) return;

        notificationService.handleCalendarRejected(userSharedCalendar);
    }

    @AfterReturning(value = "@annotation(kr.ac.kookmin.wink.planlist.notification.aop.Notify)", returning = "userSharedCalendars")
    public void notifyCalendarInvitation(JoinPoint joinPoint, List<UserSharedCalendar> userSharedCalendars) {
        userSharedCalendars.forEach(notificationService::handleCalendarInvitation);
    }

    @AfterReturning(value = "@annotation(kr.ac.kookmin.wink.planlist.notification.aop.Notify)", returning = "userSharedCalendar")
    public void notifyCalendarAccepted(JoinPoint joinPoint, UserSharedCalendar userSharedCalendar) {
        if (userSharedCalendar == null) {
            return;
        }

        notificationService.handleCalendarAccepted(userSharedCalendar);
    }

    @AfterReturning(value = "@annotation(kr.ac.kookmin.wink.planlist.notification.aop.Notify)", returning = "userSharedSchedules")
    public void notifyCalendarNewSchedule(JoinPoint joinPoint, List<UserSharedSchedule> userSharedSchedules) {
        userSharedSchedules.forEach(notificationService::handleNewSharedSchedule);
    }

    private Notify getAnnotation(JoinPoint joinPoint) {
        Method currentMethod = getCurrentMethod(joinPoint);

        return currentMethod.getAnnotation(Notify.class);
    }

    private Method getCurrentMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Method[] methods = targetClass.getMethods();

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new IllegalStateException("Method not found: " + methodName);
    }
}
