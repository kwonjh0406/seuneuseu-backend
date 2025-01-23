package kwonjh0406.sns.post.repository;

import kwonjh0406.sns.post.entity.Post;
import kwonjh0406.sns.user.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findByUser(User user, Sort sort);
}
