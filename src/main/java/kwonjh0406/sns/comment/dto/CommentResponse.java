package kwonjh0406.sns.comment.dto;

import kwonjh0406.sns.comment.entity.Comment;
import kwonjh0406.sns.util.TimeDifference;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {

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
        this.timeAgo = TimeDifference.getTimeDifference(comment.getCreatedAt());
        this.username = comment.getUser().getUsername();
        this.name = comment.getUser().getName();
    }
}
