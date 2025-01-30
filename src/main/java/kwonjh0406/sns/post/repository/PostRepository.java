package kwonjh0406.sns.post.repository;

import jakarta.transaction.Transactional;
import kwonjh0406.sns.post.entity.Post;
import kwonjh0406.sns.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findByUser(User user, Sort sort);

    @Query("SELECT p.id FROM Post p ORDER BY p.id DESC")
    Page<Long> findPostIds(Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN FETCH p.postImages " +
            "LEFT JOIN FETCH p.user " +
            "WHERE p.id IN :ids ORDER BY p.id DESC")
    List<Post> findPostsWithPage(@Param("ids") List<Long> ids);

    // 아래는 댓글 동기화

    @Modifying
    @Query("""
            UPDATE Post p
            SET p.replies = p.replies + 1, p.lastCommentedAt = CURRENT_TIMESTAMP
            WHERE p.id = :id AND p.lastCommentedAt = :lastCommentedAt
            """)
    int addReplies(Long id, Timestamp lastCommentedAt);

    @Modifying
    @Query("""
            UPDATE Post p
            SET p.replies = (
                SELECT COUNT(c)
                FROM Comment c
                WHERE c.post.id = p.id
            ), p.lastCommentedAt = CURRENT_TIMESTAMP
            WHERE p.id = :id AND p.lastCommentedAt = :lastCommentedAt
            """)
    int deleteReplies(Long id, Timestamp lastCommentedAt);
}
