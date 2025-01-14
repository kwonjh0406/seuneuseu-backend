package kwonjh0406.sns.config;

import kwonjh0406.sns.infrastructrue.CustomAuthenticationSuccessHandler;
import kwonjh0406.sns.infrastructrue.IsNewCheckFilter;
import kwonjh0406.sns.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final IsNewCheckFilter isNewCheckFilter;

    @Value("${base.url}")
    private String baseUrl;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/**").permitAll() // 인증 없이 접근 가능한 경로
                                .anyRequest().authenticated()
                        // 그 외의 경로는 인증이 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(successHandler) // OAuth2 로그인 후에 초기 설정 페이지 여부를 판단하는 핸들러
                )
                .logout(logout -> logout
                        .logoutSuccessUrl(baseUrl + "/logout-success").permitAll()
                )
                .addFilterBefore(isNewCheckFilter, UsernamePasswordAuthenticationFilter.class); // 초기 설정을 마치지 않은 사용자로 부터 요청이 들어오면 로그아웃 시킴

        return http.build();
    }
}
