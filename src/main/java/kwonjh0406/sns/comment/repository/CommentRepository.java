package kwonjh0406.sns.comment.repository;

import kwonjh0406.sns.comment.dto.CommentResponse;
import kwonjh0406.sns.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
        SELECT CommentResponse(
            c.id, c.parentCommentId, c.content, c.createdAt,
            u.profileImageUrl, u.username, u.name
        )
        FROM Comment c
        JOIN c.user u
        WHERE c.post.id = :postId
    """)
    List<CommentResponse> findByPostId(Long postId);
}
