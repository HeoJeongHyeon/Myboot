package my.mysecurity.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import my.mysecurity.dto.JoinDto;
import my.mysecurity.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        // 초기 테스트용 계정 생성
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("1234"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("1234"))
                .roles("ADMIN")
                .build();

        memberRepository.save(user);
        memberRepository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = memberRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("유저 없어오: " + username);
        }
        return user;
    }

    public void join(JoinDto joinDto) {
        if (memberRepository.existsByUsername(joinDto.getUsername())) {
            throw new RuntimeException("이미 존재하는 유저에오: " + joinDto.getUsername());
        }

        UserDetails newUser = User.builder()
                .username(joinDto.getUsername())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .roles(joinDto.getRole())
                .build();

        memberRepository.save(newUser);
    }

    public List<UserDetails> getAllMembers() {
        return memberRepository.findAll();
    }
}