package virtual.library.vl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    private boolean passwordsCorrect = true;
    private boolean mailCorrect = true;
    private boolean mailExist;
}
