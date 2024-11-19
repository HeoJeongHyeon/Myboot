package my.myjwt.domain.controller;

import lombok.RequiredArgsConstructor;
import my.myjwt.domain.dto.response.MemberResponse;
import my.myjwt.domain.service.AdminService;
import my.myjwt.domain.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/members")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        return ResponseEntity.ok(adminService.getAllMembers());
    }

    /* 나중에 Dev 프로젝트때 추가 기능 세팅해주면 될듯.*/
}
