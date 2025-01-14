package kwonjh0406.sns.config;

import kwonjh0406.sns.user.interceptor.NewUserInterceptor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${base.url}")
    private String baseUrl;

    NewUserInterceptor newUserInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000",baseUrl) // 프론트엔드 주소
                .allowedMethods("*") // 모든 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더
                .allowCredentials(true); // 쿠키와 인증 정보 허용
    }

}
