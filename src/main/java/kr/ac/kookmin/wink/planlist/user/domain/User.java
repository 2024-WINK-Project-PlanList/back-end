package kr.ac.kookmin.wink.planlist.user.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.friend.domain.FriendStatus;
import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "\"user\"")
@Entity
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nickname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "profile_image_url")
    private String profileImagePath;

    @Column(name = "song_id")
    private String songId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    //유저가 친구 요청을 보냈던 친구 관계 목록
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Friendship> following;

    //유저가 친구 요청을 받은 친구 관계 목록
    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Friendship> followers;

    //TODO: 개인캘린더 데이터 OneToOne으로 추가하기

    public List<Friendship> getFriendshipsByStatus(FriendStatus status) {
        List<Friendship> friendships = new ArrayList<>();

        if (followers != null) {
            friendships.addAll(
                    followers.stream()
                            .filter((it) -> it.getStatus() == status)
                            .toList()
            );
        }

        if (following != null) {
            friendships.addAll(
                    following.stream()
                            .filter((it) -> it.getStatus() == status)
                            .toList()
            );
        }

        return friendships;
    }
}
