package virtual.library.vl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    private boolean gotError = false;
    private boolean passwordsCorrect = true;
    private boolean mailCorrect = true;
    private boolean mailExist = true;
    private boolean mailEmpty = false;
    private boolean passwordEmpty = false;
    private boolean nicknameEmpty = false;
}
