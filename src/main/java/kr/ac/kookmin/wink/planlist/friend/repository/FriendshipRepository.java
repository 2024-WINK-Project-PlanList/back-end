package kr.ac.kookmin.wink.planlist.friend.repository;

import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findAllByFollower(User follower);

    List<Friendship> findAllByFollowing(User following);
}
