package virtual.library.vl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfoDTO {
    private boolean ifLoginSuccess;

    public LoginInfoDTO(boolean ifLoginSuccess){
           this.ifLoginSuccess = ifLoginSuccess;
    }
}
