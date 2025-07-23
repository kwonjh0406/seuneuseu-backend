package kwonjh0406.sns.post.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class CreatePostRequest {
    private String content;
    private List<MultipartFile> images;
}
