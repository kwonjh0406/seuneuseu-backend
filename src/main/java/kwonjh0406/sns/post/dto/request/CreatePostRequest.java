package kwonjh0406.sns.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 게시글 생성 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class CreatePostRequest {

    /** 게시글 본문 내용 */
    private String content;

    /** 첨부 이미지 목록 */
    private List<MultipartFile> images;

}
