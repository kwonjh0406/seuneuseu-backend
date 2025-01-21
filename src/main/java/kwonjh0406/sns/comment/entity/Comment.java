package kwonjh0406.sns.comment.entity;

import jakarta.persistence.*;
import kwonjh0406.sns.post.entity.Post;
import kwonjh0406.sns.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    private Long parentCommentId;

    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

}
