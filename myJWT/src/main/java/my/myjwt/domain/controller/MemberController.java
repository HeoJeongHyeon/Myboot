package my.myjwt.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.myjwt.domain.dao.Member;
import my.myjwt.domain.dto.request.LoginRequest;
import my.myjwt.domain.dto.request.SignupRequest;
import my.myjwt.domain.dto.response.TokenResponse;
import my.myjwt.domain.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.signup(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login attempt for email: {}", loginRequest.getEmail());
        log.info("Login successful for email: {}", loginRequest.getEmail());
        return ResponseEntity.ok()
                .body(memberService.login(loginRequest.getEmail(), loginRequest.getPassword()));

    }
}
