package kwonjh0406.sns.post.repository;

import kwonjh0406.sns.post.entity.Post;
import kwonjh0406.sns.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
