package kr.ac.kookmin.wink.planlist.notification.service;

import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import kr.ac.kookmin.wink.planlist.friend.service.FriendshipService;
import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.notification.domain.Notification;
import kr.ac.kookmin.wink.planlist.notification.domain.NotificationMessage;
import kr.ac.kookmin.wink.planlist.notification.dto.NotificationDTO;
import kr.ac.kookmin.wink.planlist.notification.exception.NotificationErrorCode;
import kr.ac.kookmin.wink.planlist.notification.repository.NotificationRepository;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.UserSharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.service.SharedCalendarService;
import kr.ac.kookmin.wink.planlist.shared.schedule.domain.UserSharedSchedule;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final FriendshipService friendshipService;
    private final SharedCalendarService sharedCalendarService;

    public Notification findByMessageType(NotificationMessage message, Long referenceId, User user) {
        return notificationRepository.findByMessageType(message, referenceId, user)
                .orElseThrow(() -> new CustomException(NotificationErrorCode.NOT_FOUND));
    }


    @Transactional
    public void sendNotification(User user, NotificationMessage message, Long referenceId) {
        Notification notification = Notification
                .builder()
                .message(message)
                .user(user)
                .referenceId(referenceId)
                .build();

        notificationRepository.save(notification);
    }

    @Transactional
    public void setNotificationRead(User user) {
        notificationRepository.findAllByUser(user)
                .stream()
                .filter(notification -> !notification.isRead())
                .forEach(Notification::read);
    }

    @Transactional
    public void handleFriendAccepted(Friendship friendship) {
        Notification requested = notificationRepository.findByMessageType(NotificationMessage.FRIEND_REQUEST, friendship.getId(), friendship.getFollowing())
                .orElseThrow(() -> new CustomException(NotificationErrorCode.FRIEND_REQUEST_NOT_FOUND));

        if (deleteNotification(requested)) {
            sendNotification(friendship.getFollower(), NotificationMessage.FRIEND_ACCEPTED, friendship.getId());
        }
    }

    @Transactional
    public void handleNewSharedSchedule(UserSharedSchedule userSharedSchedule) {
        Long calendarId = userSharedSchedule.getSharedSchedule().getSharedCalendar().getId();
        sendNotification(userSharedSchedule.getUser(), NotificationMessage.CALENDAR_NEW_SCHEDULE, calendarId);
    }

    @Transactional
    public void handleCalendarInvitation(UserSharedCalendar calendar) {
        sendNotification(calendar.getUser(), NotificationMessage.CALENDAR_INVITATION, calendar.getSharedCalendar().getId());
    }

    @Transactional
    public void handleCalendarAccepted(UserSharedCalendar calendar) {
        Long calendarId = calendar.getSharedCalendar().getId();
        Notification invited = notificationRepository.findByMessageType(NotificationMessage.CALENDAR_INVITATION, calendarId, calendar.getUser())
                .orElseThrow(() -> new CustomException(NotificationErrorCode.CALENDAR_INVITATION_NOT_FOUND));

        if (deleteNotification(invited)) {
            sendNotification(calendar.getUser(), NotificationMessage.CALENDAR_ACCEPTED, calendarId);
        }
    }

    @Transactional
    public void handleCalendarRejected(UserSharedCalendar calendar) {
        Long calendarId = calendar.getSharedCalendar().getId();
        Notification invited = notificationRepository.findByMessageType(NotificationMessage.CALENDAR_INVITATION, calendarId, calendar.getUser())
                .orElseThrow(() -> new CustomException(NotificationErrorCode.CALENDAR_INVITATION_NOT_FOUND));

        deleteNotification(invited);
    }

    @Transactional
    public boolean deleteNotification(Notification notification) {
        try {
            notificationRepository.delete(notification);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<NotificationDTO> getNotifications(User user) {
        return notificationRepository.findAllByUser(user)
                .stream()
                .map((notification) ->
                        NotificationDTO.create(
                                notification,
                                getImagePath(notification),
                                getFormattedTitle(notification),
                                getFormattedMessage(notification)
                        )
                )
                .toList();

    }

    public String getImagePath(Notification notification) {
        NotificationMessage message = notification.getMessage();
        Long referenceId = notification.getReferenceId();

        if (message == NotificationMessage.FRIEND_REQUEST) {
            Friendship friendship = friendshipService.findById(referenceId);

            return friendship.getFollower().getProfileImagePath();
        } else if (message == NotificationMessage.CALENDAR_INVITATION
                || message == NotificationMessage.CALENDAR_ACCEPTED) {
            SharedCalendar sharedCalendar = sharedCalendarService.findById(referenceId);

            return sharedCalendar.getCalendarImagePath();
        }

        return null;
    }

    public String getFormattedMessage(Notification notification) {
        NotificationMessage message = notification.getMessage();

        if (message == NotificationMessage.WELCOME) {
            String userName = notification.getUser().getNickname();

            return message.getMessage().formatted(userName);
        } else if (message == NotificationMessage.FRIEND_REQUEST) {
            Long referenceId = notification.getReferenceId();

            if (friendshipService.existsById(referenceId)) {
                Friendship friendship = friendshipService.findById(referenceId);
                String followerName = friendship.getFollower().getNickname();

                return message.getMessage().formatted(followerName);
            }
        }

        return message.getMessage();
    }

    public String getFormattedTitle(Notification notification) {
        NotificationMessage message = notification.getMessage();

        if (message == NotificationMessage.FRIEND_ACCEPTED) {
            Long referenceId = notification.getReferenceId();

            if (friendshipService.existsById(referenceId)) {
                Friendship friendship = friendshipService.findById(referenceId);
                String followingName = friendship.getFollowing().getNickname();

                return message.getTitle().formatted(followingName);
            }
        }

        return message.getTitle();
    }
}