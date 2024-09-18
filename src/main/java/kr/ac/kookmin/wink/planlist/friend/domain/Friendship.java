package kr.ac.kookmin.wink.planlist.friend.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

    @Column(name = "friend_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    public User getAnotherOne(User standardUser) {
        return isFollower(standardUser) ? following : follower;
    }

    public boolean isFollower(User user) {
        return user.getId().equals(follower.getId());
    }
}
