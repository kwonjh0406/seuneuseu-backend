package kwonjh0406.sns.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WelcomeProfileSetupDTO {

    @Pattern(regexp = "^[a-zA-Z0-9._]{3,25}$", message = "사용자 이름은 3~25자 이내의 영어, 숫자, 마침표, 밑줄만 포함할 수 있습니다.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z가-힣]{3,25}$", message = "이름은 3~25자 이내의 한글 또는 영어만 포함할 수 있습니다.")
    private String name;

    private MultipartFile profileImage;
}
