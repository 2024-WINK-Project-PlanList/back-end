package kr.ac.kookmin.wink.planlist.user.repository;

import kr.ac.kookmin.wink.planlist.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

//    @Query("SELECT u FROM User u JOIN Friendship f ON (u = f.follower OR u = f.following) WHERE f.follower.id = :userId OR f.following.id = :userId")
//    List<User> findAllByUserId(@Param("userId") Long userId);
}