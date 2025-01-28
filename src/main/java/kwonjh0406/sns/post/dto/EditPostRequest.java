package kwonjh0406.sns.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
public class EditPostRequest {

    @Size(max = 500, message = "최대 500자까지 작성할 수 있습니다.")
    @NotBlank(message = "입력은 필수입니다.")
    private String content;
}

