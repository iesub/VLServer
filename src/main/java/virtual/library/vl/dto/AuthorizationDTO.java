package virtual.library.vl.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Getter
public class AuthorizationDTO {
    private boolean authenticated;
    private String mail;
    private String nickname;
    private Collection<? extends GrantedAuthority> authorities;
}
