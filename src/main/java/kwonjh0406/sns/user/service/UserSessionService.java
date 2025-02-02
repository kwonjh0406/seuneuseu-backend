package kwonjh0406.sns.user.service;

import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import kwonjh0406.sns.user.dto.UsernameDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserSessionService {

    public UsernameDto getCurrentUsername() {
        UsernameDto usernameDto = new UsernameDto();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomOAuth2User oAuth2User) {
            usernameDto.setUsername(oAuth2User.getUser().getUsername());
        }

        return usernameDto;
    }
}
