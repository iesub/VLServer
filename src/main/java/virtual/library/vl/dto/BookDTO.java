package virtual.library.vl.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import virtual.library.vl.entity.Author;
import virtual.library.vl.entity.Book;
import virtual.library.vl.entity.BookGenre;
import virtual.library.vl.entity.BookTag;
import virtual.library.vl.service.BookService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Jacksonized
@Builder
public class BookDTO {
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private String description;
    String author;
    String bookGenre;
    String tags;

    @Transactional
    public Book transformDTO(BookService bookService, MultipartFile logo){
        Book book = new Book();
        book.setName(this.name);
        book.setReleaseDate(this.releaseDate);
        book.setDescription(this.description);
        book.setAuthor(bookService.getAuthor(Long.valueOf(author)));
        book.setBookGenre(bookService.getBookGenre(Long.valueOf(bookGenre)));
        String[] tagId = tags.split(",");
        for (int i = 0; i < tagId.length; i++){
            book.addTag(bookService.getBookTag(Long.valueOf(tagId[i])));
        }
        try {
            book.setLogo(logo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }
}
