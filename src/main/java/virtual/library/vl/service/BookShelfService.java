package virtual.library.vl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import virtual.library.vl.entity.Book;
import virtual.library.vl.entity.BookShelf;
import virtual.library.vl.entity.User;
import virtual.library.vl.repository.BookRepository;
import virtual.library.vl.repository.BookShelfRepository;

import java.util.List;

@Service
public class BookShelfService {
    @Autowired
    BookShelfRepository bookShelfRepository;
    @Autowired
    BookRepository bookRepository;

    public void createShelf(User user, String name){
        BookShelf bookShelf = new BookShelf();
        bookShelf.setName(name);
        bookShelf.setOwner(user);
        bookShelfRepository.save(bookShelf);
    }

    @Transactional
    public void deleteShelf(Long id){
        bookShelfRepository.deleteBooksFromShelf(id);
        bookShelfRepository.deleteById(id);
    }

    @Transactional
    public boolean addBookToShelf(Long shelfId, Long bookId){
        if (bookRepository.findBookByShelf(shelfId, bookId) == null) {
            bookShelfRepository.addBookToShelf(shelfId, bookId);
            BookShelf shelf = bookShelfRepository.getById(shelfId);
            shelf.setBookCount(shelf.getBookCount()+1);
            bookShelfRepository.save(shelf);
            return true;
        } else {
            return false;
        }
    }

    public List<BookShelf> getShelvesByUser(User owner){
        return bookShelfRepository.getBookShelfByOwnerOrderByNameAsc(owner);
    }

    public List<Book> getBooksFromShelf(Long shelfId){
        return bookRepository.getBooksFromShelf(shelfId);
    }

    public BookShelf getShelfById(Long id){
        return bookShelfRepository.getBookShelfByIdCustom(id);
    }

    @Transactional
    public void deleteBookFromShelf(Long bookID, Long shelfId){
        bookShelfRepository.deleteBookFromShelf(bookID, shelfId);
        BookShelf shelf = bookShelfRepository.getBookShelfByIdCustom(shelfId);
        shelf.setBookCount(shelf.getBookCount()-1);
    }
}
