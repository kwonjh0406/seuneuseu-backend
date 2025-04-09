package kwonjh0406.sns.util;

import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import kwonjh0406.sns.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomOAuth2User oAuth2User) {
            return oAuth2User.getUser();
        }

        return null;
    }

}
