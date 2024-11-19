package my.myjwt.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.myjwt.domain.dao.Member;
import my.myjwt.domain.dao.Role;

@Getter
@AllArgsConstructor
@Builder
public class MemberResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .role(member.getRole())
                .build();
    }
}
