package kr.ac.kookmin.wink.planlist.notification.repository;

import kr.ac.kookmin.wink.planlist.notification.domain.Notification;
import kr.ac.kookmin.wink.planlist.notification.domain.NotificationMessage;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUser(User user);

    @Query(
            "SELECT n FROM Notification n " +
            "WHERE n.message = :message" +
            " AND n.referenceId = :referenceId" +
            " AND n.user = :user"
    )
    Optional<Notification> findByMessageType(
            @Param("message") NotificationMessage message,
            @Param("referenceId") Long referenceId,
            @Param("user") User user
    );

}
