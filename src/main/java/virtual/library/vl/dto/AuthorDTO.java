package virtual.library.vl.dto;

import lombok.Getter;
import lombok.Setter;
import virtual.library.vl.entity.Author;

@Getter
@Setter
public class AuthorDTO {
    Long id;
    String name;

    public AuthorDTO(){}

    public AuthorDTO(Author author){
        id = author.getId();
        name = author.getName();
    }
}
