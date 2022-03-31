package virtual.library.vl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import virtual.library.vl.entity.Book;
import virtual.library.vl.service.BookService;

import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    BookService bookService;

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
}