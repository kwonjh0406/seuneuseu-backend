package kwonjh0406.sns.infrastructrue;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomOAuth2User oAuth2User) { // 로그인한 사용자가 OAuth2 사용자인 경우

            if (oAuth2User.getUser().getIsNew()) { // 사용자가 아직 초기 설정을 안한 사용자인 경우

                System.out.println(oAuth2User.getUser().getUsername());
                response.sendRedirect("http://localhost:3000/welcome"); // 초기 설정 페이지로 리다이렉션
                return;
            }
        }

        response.sendRedirect("http://localhost:3000/");
    }
}

