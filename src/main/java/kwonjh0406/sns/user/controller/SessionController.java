package kwonjh0406.sns.user.controller;

import kwonjh0406.sns.user.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/username")
    public Map<String, String> getCurrentUsername() {
        return sessionService.getCurrentUsername();
    }
}