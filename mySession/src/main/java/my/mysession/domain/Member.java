package my.mysession.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Member {
    private String username;
    private String email;
    private String password;
}