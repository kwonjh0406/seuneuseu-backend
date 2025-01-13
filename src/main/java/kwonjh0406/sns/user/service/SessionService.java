package kwonjh0406.sns.user.service;

import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionService {

    public Map<String, String> getCurrentUsername() {
        Map<String, String> response = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 인증되지 않은 경우
        if(authentication == null || !authentication.isAuthenticated()) {
            response.put("username", null);
            return response;
        }

        // OAuth2로 로그인된 경우
        Object principal = authentication.getPrincipal();
        if(principal instanceof CustomOAuth2User oAuth2User) {
            response.put("username", oAuth2User.getUser().getUsername());
            return response;
        }

        // 그 외
        response.put("username", null);
        return response;
    }
}
