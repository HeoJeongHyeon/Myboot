package my.myjwt.domain.service;

import lombok.RequiredArgsConstructor;
import my.myjwt.domain.dao.Member;
import my.myjwt.domain.dao.Role;
import my.myjwt.domain.dto.request.SignupRequest;
import my.myjwt.domain.dto.response.MemberResponse;
import my.myjwt.domain.dto.response.TokenResponse;
import my.myjwt.domain.repository.MemberRepository;
import my.myjwt.global.security.jwt.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberResponse signup(SignupRequest signupRequest) {

        if (memberRepository.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일");
        }

        Member member = Member.builder()
                .email(signupRequest.getEmail())
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(Role.USER)
                .build();
        Member savedMember = memberRepository.save(member);
        return new MemberResponse(savedMember.getId(), savedMember.getUsername(), savedMember.getEmail(), savedMember.getRole());
    }

    public TokenResponse login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원"));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호");
        }
        //토큰 발급
        String token = jwtTokenProvider.createToken(member.getEmail(), Collections.singletonList(member.getRole().name()));
        return TokenResponse.builder()
                .tokenType("Bearer")
                .accessToken(token)
                .build();
    }



}
