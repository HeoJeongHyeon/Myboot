package my.mysecurity.controller;

import lombok.RequiredArgsConstructor;
import my.mysecurity.dto.JoinDto;
import my.mysecurity.service.CustomUserDetailsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/security")
@RequiredArgsConstructor
public class HomeController {
    private final CustomUserDetailsService userDetailsService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "시큐리티 연습 페이지에오.");
        return "homepage";
    }

    @GetMapping("/user")
    public String userPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("message", userDetails.getUsername() + "님 유저 페이지에오");
        return "userpage";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("message", userDetails.getUsername() + "님 관리자 페이지에오");
        model.addAttribute("memberList", userDetailsService.getAllMembers());
        return "adminpage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "loginpage";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("joinDto", new JoinDto());
        return "joinpage";
    }

    @PostMapping("/join")
    public String joinProcess(@ModelAttribute JoinDto joinDto, RedirectAttributes redirectAttributes) {
        try {
            if (joinDto.getRole() == null || joinDto.getRole().isEmpty()) {
                joinDto.setRole("USER");
            }

            userDetailsService.join(joinDto);
            redirectAttributes.addFlashAttribute("message", "회원가입 완료!");
            return "redirect:/security/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/security/join";
        }
    }
}