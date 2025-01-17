package kwonjh0406.sns.post.entity;


import jakarta.persistence.*;
import kwonjh0406.sns.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @OneToOne < 4시간 날린 삽질 주범. 졸릴 땐 잠을 자자.
    @ManyToOne(fetch = FetchType.EAGER)
    private User user; // 게시글 작성자

    @Lob
    private String content; // 게시글 내용

    private Long likes; // 좋아요 수

    private Long replies; // 댓글 수

    @CreationTimestamp
    private Timestamp createdAt; // 게시글 작성일자
}
