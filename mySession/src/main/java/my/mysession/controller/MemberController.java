package my.mysession.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.mysession.Service.MemberService;
import my.mysession.domain.Member;
import my.mysession.dto.ApiResponse;
import my.mysession.dto.MemberDto;
import my.mysession.session.SessionConst;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<MemberDto> signup(@RequestBody Member member) {
        memberService.join(member);
        log.info("login info = email:{}, password:{}", member.getEmail(), member.getPassword());
        return ApiResponse.success(convertToDto(member));
    }

    @PostMapping("/login")
    public ApiResponse<MemberDto> login(@RequestBody MemberDto.LoginRequest request, HttpServletRequest httpServletRequest) {
        try {
            Member member = memberService.login(request.getEmail(), request.getPassword());
            log.info("Service login info = email:{}, password:{}", member.getEmail(), member.getPassword());

            // 대충 실패만 처리했다치고 .
            if (member == null) {
                return ApiResponse.error("회원없음 ");
            }

            // 성공 처리
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, member);
            return ApiResponse.success(convertToDto(member));
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest httpServletRequest) {
        // 여기에 세션 삭제
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
//            log.info("Session ID: {}", session.getId());
        }

//        log.info("Session ID: {}", session.getId());
        return ApiResponse.success(null);
    }

    @GetMapping("/myinfo")
    public ApiResponse<MemberDto> getMyInfo(HttpServletRequest httpServletRequest) {
        // 세션에서 사용자 정보를 가져오는 로직
        HttpSession session = httpServletRequest.getSession();
        if (session == null) {
            return ApiResponse.error("로그인이 필요합니다.");
        }

        // 회원정보조회
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return ApiResponse.success(null);
        }
        return ApiResponse.success(convertToDto(loginMember));
    }

    // DTO 변환
    private MemberDto convertToDto(Member member) {
        MemberDto dto = new MemberDto();
        dto.setUsername(member.getUsername());
        dto.setEmail(member.getEmail());
        return dto;
    }
}