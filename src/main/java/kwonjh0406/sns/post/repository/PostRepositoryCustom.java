package kwonjh0406.sns.post.repository;

import kwonjh0406.sns.post.entity.Post;

public interface PostRepositoryCustom {
    int addReplies(Post post);
    int deleteReplies(Post post);
}
