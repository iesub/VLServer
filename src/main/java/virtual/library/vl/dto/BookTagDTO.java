package virtual.library.vl.dto;

import lombok.Getter;
import lombok.Setter;
import virtual.library.vl.entity.Author;
import virtual.library.vl.entity.BookTag;

@Getter
@Setter
public class BookTagDTO {
    Long id;
    String name;

    public BookTagDTO(){}

    public BookTagDTO(BookTag bookTag){
        id = bookTag.getId();
        name = bookTag.getName();
    }
}
