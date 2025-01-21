package kwonjh0406.sns.comment.dto;

import kwonjh0406.sns.comment.entity.Comment;
import kwonjh0406.sns.post.util.TimeDifference;
import lombok.Data;

@Data
public class CommentResponse {

    private static final TimeDifference timeDifference = new TimeDifference();

    private Long id;
    private Long parentId;
    private String content;
    private String profileImageUrl;
    private String timeAgo;
    private String username;
    private String name;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.parentId = comment.getParentCommentId();
        this.content = comment.getContent();
        this.profileImageUrl = comment.getUser().getProfileImageUrl();
        this.timeAgo = timeDifference.getTimeDifference(comment.getCreatedAt());
        this.username = comment.getUser().getUsername();
        this.name = comment.getUser().getName();
    }
}
