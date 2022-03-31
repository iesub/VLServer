package virtual.library.vl.dto;

import lombok.Getter;
import lombok.Setter;
import virtual.library.vl.entity.Author;
import virtual.library.vl.entity.BookGenre;

@Getter
@Setter
public class BookGenreDTO {
    Long id;
    String name;

    public BookGenreDTO(){}

    public BookGenreDTO(BookGenre bookGenre){
        id = bookGenre.getId();
        name = bookGenre.getName();
    }
}
