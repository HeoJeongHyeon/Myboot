package my.mysession.dto;

import lombok.*;


@Getter @Setter
@ToString
public class MemberDto {
    private String username;
    private String email;

    // 로그인 요청용
    @Getter @Setter
    public static class LoginRequest {
        private String email;
        private String password;
    }
}