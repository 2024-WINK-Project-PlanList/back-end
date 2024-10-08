package kr.ac.kookmin.wink.planlist.user.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import lombok.*;

import java.sql.Timestamp;

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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
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
  
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private IndividualCalendar individualCalendar;

    public void updateUserProfile(String nickname, String songId, String comment) {
        this.nickname = nickname;
        this.songId = songId;
        this.comment = comment;
    }
}
