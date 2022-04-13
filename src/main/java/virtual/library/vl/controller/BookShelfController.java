package virtual.library.vl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import virtual.library.vl.entity.BookShelf;
import virtual.library.vl.entity.User;
import virtual.library.vl.service.BookService;
import virtual.library.vl.service.BookShelfService;

import java.util.Objects;

@Controller
public class BookShelfController {
    @Autowired
    BookService bookService;
    @Autowired
    BookShelfService bookShelfService;

    @GetMapping("/get/shelves")
    public String getShelves(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        model.addAttribute("response", bookShelfService.getShelvesByUser(user));
        return "jsonTemplate";
    }

    @PostMapping("/add/shelf")
    public String addShelf(Model model, BookShelf bookShelf){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        if (bookShelf.getName().equals("") || bookShelf.getName() == null){
            model.addAttribute("response", "NAME_EMPTY_ERROR");
        } else {
            model.addAttribute("response", "SUCCESS");
            bookShelfService.createShelf(user, bookShelf.getName());
        }
        return "jsonTemplate";
    }

    @PostMapping("/delete/shelf")
    public String deleteShelf(Model model, @RequestParam("id") Long shelfId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        BookShelf shelf = bookShelfService.getShelfById(shelfId);
        if (!Objects.equals(shelf.getOwner().getId(), user.getId())){
            model.addAttribute("response", "OWNERSHIP_ERROR");
        } else {
            bookShelfService.deleteShelf(shelfId);
            model.addAttribute("response", "SUCCESS");
        }
        return "jsonTemplate";
    }

    @PostMapping("/add/book-to-shelf")
    public String addBookToShelf(Model model, @RequestParam("bookId") Long bookID,
                                 @RequestParam("shelfId") Long shelfId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        BookShelf shelf = bookShelfService.getShelfById(shelfId);
        if (!Objects.equals(shelf.getOwner().getId(), user.getId())){
            model.addAttribute("response", "OWNERSHIP_ERROR");
            return "jsonTemplate";
        }
        if (bookShelfService.addBookToShelf(shelfId, bookID)){
            model.addAttribute("response", "SUCCESS");
        } else {
            model.addAttribute("response", "ALREADY_ON_SHELF");
        }
        return "jsonTemplate";
    }

    @GetMapping("/get/shelf")
    public String getShelf(@RequestParam("id") Long id, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        BookShelf shelf = bookShelfService.getShelfById(id);
        if (!Objects.equals(shelf.getOwner().getId(), user.getId())){
            model.addAttribute("response", "OWNERSHIP_ERROR");
        } else {
            model.addAttribute("response", shelf);
        }
        return "jsonTemplate";
    }

    @GetMapping("/get/books-from-shelf")
    public String getBooksFromShelf(@RequestParam("id") Long shelfIDd, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        BookShelf shelf = bookShelfService.getShelfById(shelfIDd);
        if (!Objects.equals(shelf.getOwner().getId(), user.getId())){
            model.addAttribute("response", "OWNERSHIP_ERROR");
        } else {
            model.addAttribute("response",  bookShelfService.getBooksFromShelf(shelfIDd));
        }
        return "jsonTemplate";
    }

    @PostMapping("/delete/book-from-shelf")
    public String deleteBookFromShelf(@RequestParam("bookId") Long bookId, @RequestParam("shelfId") Long shelfId, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        BookShelf shelf = bookShelfService.getShelfById(shelfId);
        if (!Objects.equals(shelf.getOwner().getId(), user.getId())){
            model.addAttribute("response", "OWNERSHIP_ERROR");
        } else {
            bookShelfService.deleteBookFromShelf(bookId, shelfId);
            model.addAttribute("response", "SUCCESS");
        }
        return "jsonTemplate";
    }
}
