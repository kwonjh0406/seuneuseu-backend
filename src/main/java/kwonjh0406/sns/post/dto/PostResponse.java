package kwonjh0406.sns.post.dto;

import kwonjh0406.sns.post.entity.Post;
import kwonjh0406.sns.post.util.TimeDifference;
import lombok.Getter;

import java.util.List;

@Getter
public class PostResponse {

    private static final TimeDifference timeDifference = new TimeDifference();

    private final Long postId;
    private final String content;
    private final Long likes;
    private final Long replies;
    private final String timeAgo;
    private final String username;
    private final String name;
    private final String profileImageUrl;
    private final List<String> imageUrls;

    public PostResponse(Post post, List<String> imageUrls) {
        this.postId = post.getId();
        this.content = post.getContent();
        this.likes = post.getLikes();
        this.replies = post.getReplies();
        this.timeAgo = timeDifference.getTimeDifference(post.getCreatedAt());
        this.username = post.getUser().getUsername();
        this.name = post.getUser().getName();
        this.profileImageUrl = post.getUser().getProfileImageUrl();
        this.imageUrls = imageUrls;
    }
}