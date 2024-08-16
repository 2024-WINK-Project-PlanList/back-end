package kr.ac.kookmin.wink.planlist.user.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import lombok.*;

import java.sql.Timestamp;

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

    @Column(name = "email")
    private String email;

    @Column(name = "profile_image_url")
    private String profileImagePath;

    @Column(name = "song_id")
    private String songId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    //TODO: 개인캘린더 데이터 OneToOne으로 추가하기
    @OneToOne(mappedBy = "user")
    private IndividualCalendar individualCalendar;
}
