package kr.ac.kookmin.wink.planlist.page.service;

import kr.ac.kookmin.wink.planlist.friend.service.FriendshipService;
import kr.ac.kookmin.wink.planlist.page.dto.FriendCommentDTO;
import kr.ac.kookmin.wink.planlist.page.dto.MainPageResponseDTO;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final FriendshipService friendshipService;
    //private final NotificationService notificationService;

    public MainPageResponseDTO getMainPage(User user) {
        List<FriendCommentDTO> friendComments = friendshipService.findAllFriendsByUser(user)
                .stream()
                .map((friendDTO) -> FriendCommentDTO.create(friendDTO.getFriend()))
                .toList();

//        long newNotificationCount = notificationService.getNotifications(user)
//                .stream()
//                .filter((notification) -> !notification.isRead())
//                .count();

        long newNotificationCount = 1;

        return MainPageResponseDTO
                .builder()
                .friendComments(friendComments)
                .newNotificationCount(newNotificationCount)
                .build();
    }
}
