package kwonjh0406.sns.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // DB 식별자

    @Column(unique = true)
    String username; // 서비스에서 노출되는 고유한 사용자 아이디

    String name; // 프로필 이름

    String password; // 암호화된 비밀번호, OAuth2 유저는 사용 X

    String profileImageUrl; // 프로필 사진

    String bio; // 프로필 한줄소개

    Long Follower; // 나를 팔로잉하는 사용자 수

    Long Following; // 내가 팔로우하는 사용자 수

    @Column(unique = true)
    String email;

    Boolean isNew = true; // true인 경우는 초기 사용자 설정을 마쳐야 서비스 이용 가능

    @CreationTimestamp
    Timestamp createdAt; // 사용자 생성일자
}
