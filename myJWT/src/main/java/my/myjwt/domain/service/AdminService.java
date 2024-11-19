package my.myjwt.domain.service;

import lombok.RequiredArgsConstructor;
import my.myjwt.domain.dto.response.MemberResponse;
import my.myjwt.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;

    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    /* 관리자 전용 페이지 추가해야하는데 회원 목록, 메뉴추가, 삭제, 권리면 괜찮으려나 */
}
