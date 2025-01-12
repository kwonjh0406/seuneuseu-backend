package kwonjh0406.sns.post.repository;

import kwonjh0406.sns.post.entity.Post;
import kwonjh0406.sns.post.entity.PostImage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostImageRepository extends CrudRepository<PostImage, Long> {
    public List<PostImage> findByPost(Post post);
}
