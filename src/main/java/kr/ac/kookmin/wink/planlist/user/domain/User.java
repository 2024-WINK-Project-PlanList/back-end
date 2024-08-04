package kr.ac.kookmin.wink.planlist.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "user")
@Entity
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nickname;

    private String email;

    @Column(name = "profile_image_url")
    private String profileImagePath;

    @Column(name = "song_id")
    private String songId;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
