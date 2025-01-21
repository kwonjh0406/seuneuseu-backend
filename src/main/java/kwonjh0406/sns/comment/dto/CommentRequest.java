package kwonjh0406.sns.comment.dto;

import lombok.Data;

@Data
public class CommentRequest {
    String content;
    Long parentId;
}
