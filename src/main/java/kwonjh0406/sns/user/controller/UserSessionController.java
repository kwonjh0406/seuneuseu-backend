package kwonjh0406.sns.user.controller;

import kwonjh0406.sns.user.dto.UsernameDto;
import kwonjh0406.sns.user.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserSessionController {

    private final UserSessionService userSessionService;

    @GetMapping("/username")
    public UsernameDto getCurrentUsername() {
        return userSessionService.getCurrentUsername();
    }
}