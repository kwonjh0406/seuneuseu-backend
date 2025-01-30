package kwonjh0406.sns.comment.dto;

import kwonjh0406.sns.comment.entity.Comment;
import kwonjh0406.sns.util.TimeDifference;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {
    private Long id;
    private Long parentId;
    private String content;
    private String profileImageUrl;
    private String username;
    private String name;
    private String timeAgo; // 시간을 문자열로 저장

    public CommentResponse(Long id, Long parentId, String content, Timestamp createdAt, String profileImageUrl,
                           String username, String name) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
        this.profileImageUrl = profileImageUrl;
        this.username = username;
        this.name = name;
        this.timeAgo = TimeDifference.getTimeDifference(createdAt); // 여기서 시간 변환
    }
}
