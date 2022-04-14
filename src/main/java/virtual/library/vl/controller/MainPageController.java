package virtual.library.vl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import virtual.library.vl.dto.FiltersDTO;
import virtual.library.vl.entity.Book;
import virtual.library.vl.service.BookService;

import java.util.List;

@Controller
@Transactional
public class MainPageController {

    @Autowired
    BookService bookService;

    @GetMapping("/")
    public String logout(Model model){
        model.addAttribute("response", "LOGOUT");
        return "jsonTemplate";
    }

    @GetMapping("/get/book-count")
    public String getBooCount(Model model){
        model.addAttribute("response", bookService.countBooks());
        return "jsonTemplate";
    }

    @GetMapping("/get/book-list")
    public String getBooks(@RequestParam("offset") Long offset, Model model){
        List<Book> books= bookService.selectBooksWithOffset((offset-1)*20);
        model.addAttribute("response", books);
        return "jsonTemplate";
    }

    @PostMapping("/get/book-by-name")
    public String getBookByName(@RequestParam("query") String query, @RequestParam("offset") Long offset,
                                Model model){
        List<Book> books = bookService.selectBooksByNameQuery(query, (offset-1)*20);
        model.addAttribute("response", books);
        model.addAttribute("responseCount", bookService.countFindBookByName(query));
        return "jsonTemplate";
    }

    @PostMapping("/get/book-by-filter")
    public String getBookByFilter(FiltersDTO filters, Model model){
        List<Book> books = bookService.selectByFilters(filters);
        model.addAttribute("response", books);
        model.addAttribute("responseCount", bookService.countByFilters(filters));
        return "jsonTemplate";
    }

    @PostMapping("/delete/book")
    public String deleteBook(Model model, @RequestParam("bookId") Long id){
        bookService.deleteBook(id);
        return "jsonTemplate";
    }

}
