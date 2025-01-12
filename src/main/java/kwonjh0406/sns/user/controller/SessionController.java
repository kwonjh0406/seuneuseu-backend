package kwonjh0406.sns.user.controller;

import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @GetMapping("/username")
    public Map<String, String> getCurrentUsername() {
        Map<String, String> response = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()) { // 인증 정보가 있는 경우
            Object principal = authentication.getPrincipal();

            if(principal instanceof CustomOAuth2User customOAuth2User) { // OAuth2 세션인 경우
                response.put("username", customOAuth2User.getUser().getUsername());
                return response;
            }
        }

        // 위 케이스를 제외하고는 전부 정보 반환 X
        response.put("username", null);
        return response;
    }

    @GetMapping
    public Map<String, Boolean> checkSession() {
        Map<String, Boolean> response = new HashMap<>();

        // 현재 사용자가 인증되었는지 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken));

        // 로그인 여부를 응답에 포함
        response.put("isLoggedIn", isLoggedIn);
        return response;
    }

}
