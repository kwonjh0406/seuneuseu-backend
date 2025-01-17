package kwonjh0406.sns.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEditResponse {
    private String username;
    private String name;
    private String profileImageUrl;
    private String bio;
}
