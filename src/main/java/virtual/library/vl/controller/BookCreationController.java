package virtual.library.vl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import virtual.library.vl.dto.AuthorDTO;
import virtual.library.vl.dto.BookDTO;
import virtual.library.vl.dto.BookGenreDTO;
import virtual.library.vl.dto.BookTagDTO;
import virtual.library.vl.entity.*;
import virtual.library.vl.service.BookService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookCreationController {
    @Autowired
    BookService bookService;

    @PostMapping("/add/author")
    public String addAuthor(Author author, Model model){
        if (author.getName().equals("") || author.getName() == null){
            model.addAttribute("response", "ERROR_NAME_EMPTY");
            return "jsonTemplate";
        }
        if (bookService.addAuthor(author)){
            model.addAttribute("response", "SUCCESS");
        } else {
            model.addAttribute("response", "ERROR_ALREADY_EXISTS");
        }
        return "jsonTemplate";
    }

    @GetMapping("/get/authors")
    public String getAuthors(Model model){
        List<Author> authors = bookService.getAuthors();
        List<AuthorDTO> authorDTOS = new ArrayList<>();
        for (int i = 0; i < authors.size(); i++){
            authorDTOS.add(new AuthorDTO(authors.get(i)));
        }
        model.addAttribute("response", authorDTOS);
        return "jsonTemplate";
    }

    @PostMapping("/add/book-genre")
    public String addBookGenre(BookGenre bookGenre, Model model){
        if (bookGenre.getName().equals("") || bookGenre.getName() == null){
            model.addAttribute("response", "ERROR_NAME_EMPTY");
            return "jsonTemplate";
        }
        if (bookService.addBookGenre(bookGenre)){
            model.addAttribute("response", "SUCCESS");
        } else {
            model.addAttribute("response", "ERROR_ALREADY_EXISTS");
        }
        return "jsonTemplate";
    }

    @GetMapping("/get/book-genres")
    public String getBookGenres(Model model){
        List<BookGenre> genres = bookService.getBookGenres();
        List<BookGenreDTO> bookGenreDTOS = new ArrayList<>();
        for (int i = 0; i < genres.size(); i++){
            bookGenreDTOS.add(new BookGenreDTO(genres.get(i)));
        }
        model.addAttribute("response", bookGenreDTOS);
        return "jsonTemplate";
    }

    @PostMapping("/add/book-tag")
    public String addBookTag(BookTag bookTag, Model model){
        if (bookTag.getName().equals("") || bookTag.getName() == null){
            model.addAttribute("response", "ERROR_NAME_EMPTY");
            return "jsonTemplate";
        }
        if (bookService.addBookTag(bookTag)){
            model.addAttribute("response", "SUCCESS");
        } else {
            model.addAttribute("response", "ERROR_ALREADY_EXISTS");
        }
        return "jsonTemplate";
    }

    @GetMapping("/get/book-tags")
    public String getBookTags(Model model){
        List<BookTag> tags = bookService.getBookTags();
        List<BookTagDTO> bookTagDTOS = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++){
            bookTagDTOS.add(new BookTagDTO(tags.get(i)));
        }
        model.addAttribute("response", bookTagDTOS);
        return "jsonTemplate";
    }

    @Transactional
    @PostMapping("/add/book")
    public String addBook(@RequestParam("dataDTO") String bookDTOString, @RequestParam("logo") MultipartFile logo,
                          @RequestParam("book") MultipartFile bookFile, Model model) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        BookDTO bookDTO = mapper.readValue(bookDTOString, BookDTO.class);

        List<String> errors = new ArrayList<>();
        if (bookDTO.getName() == null || bookDTO.getName().equals("")){
            errors.add("ERROR_NAME_EMPTY");
        }
        if (bookDTO.getDescription() == null || bookDTO.getDescription().equals("")){
            errors.add("ERROR_DESCRIPTION_EMPTY");
        }
        if (bookDTO.getAuthor() == null || bookDTO.getAuthor().equals("")){
            errors.add("ERROR_AUTHOR_EMPTY");
        }
        if (bookDTO.getBookGenre() == null || bookDTO.getBookGenre().equals("")){
            errors.add("ERROR_GENRE_EMPTY");
        }
        if (bookDTO.getReleaseDate() == null || bookDTO.getReleaseDate().equals("")){
            errors.add("ERROR_RELEASE_DATE_EMPTY");
        }
        if (bookDTO.getTags() == null || bookDTO.getTags().equals("")){
            errors.add("ERROR_TAGS_EMPTY");
        }
        if (errors.size()==0){
            Book book = bookDTO.transformDTO(bookService, logo);
            model.addAttribute("response", book);
            if (bookService.addBook(book)){
                book = bookService.getBook(book.getName());
                model.addAttribute("response", "SUCCESS");
                bookService.transformPDFToPages(bookFile, book);
            } else {
                errors.add("ERROR_NAME_EXIST");
                model.addAttribute("response", errors);
                return "jsonTemplate";
            }
        } else {
            model.addAttribute("response", errors);
        }
        return "jsonTemplate";
    }
}
