package kwonjh0406.sns.infrastructrue;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class IsNewCheckFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/api/welcome-profile-setup")) {
            filterChain.doFilter(request, response); // 필터를 거치지 않고 바로 다음 필터로 이동
            return;
        }

        if (request.getRequestURI().startsWith("/api")) {
            // 필터 로직
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) { // 로그인된 사용자가 존재하는 경우

                Object principal = authentication.getPrincipal();

                if (principal instanceof CustomOAuth2User oAuth2User) { // 로그인 된 사용자가 OAuth2 사용자인 경우
                    if (oAuth2User.getUser().getIsNew()) { // 사용자가 초기 설정을 마치지 않은 경우

                        System.out.println("세션 날릴게~");

                        request.getSession().invalidate(); // 세션을 해제 시킴
                        SecurityContextHolder.clearContext();
                        return;
                    }
                }
            }
        }


        filterChain.doFilter(request, response);
    }
}
