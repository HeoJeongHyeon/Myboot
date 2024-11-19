package my.myjwt;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import my.myjwt.domain.dao.Member;
import my.myjwt.domain.dao.Role;
import my.myjwt.domain.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminInitializer {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initializeAdmin() {
        // 관리자가 이미 존재하는지 확인
        if (!memberRepository.existsByRole(Role.ADMIN)) {
            Member admin = Member.builder()
                    .username("admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("admin123!@#"))
                    .role(Role.ADMIN)
                    .build();
            memberRepository.save(admin);
        }
    }
}