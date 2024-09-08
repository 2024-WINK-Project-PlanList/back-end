package kr.ac.kookmin.wink.planlist.user.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.friend.domain.FriendStatus;
import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import kr.ac.kookmin.wink.planlist.todolist.model.Todolist;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private Timestamp createdAt;

    //유저가 친구 요청을 보냈던 친구 관계 목록
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Friendship> following;

    //유저가 친구 요청을 받은 친구 관계 목록
    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Friendship> followers;
  
    @OneToOne(mappedBy = "user")
    private IndividualCalendar individualCalendar;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Todolist> todolists = new ArrayList<>();


    public void updateUserProfile(String nickname, String songId, String comment) {
        this.nickname = nickname;
        this.songId = songId;
        this.comment = comment;
    }

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
