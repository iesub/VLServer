package virtual.library.vl.dto;


import lombok.Getter;
import lombok.Setter;
import virtual.library.vl.entity.Author;
import virtual.library.vl.entity.BookGenre;
import virtual.library.vl.entity.BookTag;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class FiltersDTO {
    Author author;
    BookGenre genre;
    List<BookTag> tag;
    Long offset;
}
