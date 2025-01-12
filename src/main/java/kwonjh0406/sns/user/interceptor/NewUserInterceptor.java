package kwonjh0406.sns.user.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class NewUserInterceptor implements HandlerInterceptor {


//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            if (authentication.getPrincipal() instanceof CustomOAuth2User) {
//                CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
//
//                // 신규 사용자이고, 현재 페이지가 '/welcome'이 아닐 때 리다이렉션
//                if (oAuth2User.getUser().getIsNew()) {
//                    // 리다이렉션 경로 수정
//                    response.sendRedirect("http://localhost:3000/welcome");
//                    return false; // 요청 진행 중단
//                }
//            }
//        }
//
//        return true; // 정상 처리
//    }
}
