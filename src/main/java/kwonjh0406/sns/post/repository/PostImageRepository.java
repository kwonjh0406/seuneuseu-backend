package kwonjh0406.sns.post.repository;

import kwonjh0406.sns.post.dto.PostImageResponse;
import kwonjh0406.sns.post.entity.Post;
import kwonjh0406.sns.post.entity.PostImage;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostImageRepository extends CrudRepository<PostImage, Long> {
    List<PostImage> findByPost(Post post);

    @Query(value = "SELECT imageUrl FROM PostImage WHERE post_id = :postId", nativeQuery = true)
    List<String> findAllImageUrlsByPostId(Long postId);

    List<PostImage> findAllImageResponsesByUserId(Sort sort, Long userId);
}
