package kr.ac.kookmin.wink.planlist.page.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainPageResponseDTO {
    private List<FriendCommentDTO> friendComments;
    private Long newNotificationCount;
}
