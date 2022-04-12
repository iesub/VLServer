package virtual.library.vl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import virtual.library.vl.service.BookService;

@Controller
public class BookReadController {
    @Autowired
    BookService bookService;

    @GetMapping("/get/page-amount")
    public String getPageAmount(Model model, @RequestParam("bookId") Long id){
        model.addAttribute("response", bookService.countPagesByBook(id));
        return "jsonTemplate";
    }

    @GetMapping("/get/book-by-id")
    public String getBookById(Model model, @RequestParam("bookId") Long id){
        model.addAttribute("response", bookService.getBook(id));
        return "jsonTemplate";
    }

    @GetMapping("/get/book-page")
    public String getBookByIdAndOffset(Model model, @RequestParam("bookId") Long id,
                                       @RequestParam("offset") Long offset){
        model.addAttribute("response", bookService.getPageByBookAndOffset(id, offset-1));
        return "jsonTemplate";
    }

}
