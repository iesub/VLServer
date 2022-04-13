package virtual.library.vl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import virtual.library.vl.entity.BookShelf;
import virtual.library.vl.entity.User;

import java.util.List;

public interface BookShelfRepository extends JpaRepository<BookShelf, Long> {
    @Modifying
    @Query(value = "insert into books_shelves values (?2, ?1)", nativeQuery = true)
    void addBookToShelf(Long bookShelfId, Long bookId);
    @Modifying
    @Query(value = "delete from books_shelves where shelves_id = ?1", nativeQuery = true)
    void deleteBooksFromShelf(Long shelfId);
    List<BookShelf> getBookShelfByOwnerOrderByNameAsc(User owner);
    @Query(value = "select * from shelf where id = ?1", nativeQuery = true)
    BookShelf getBookShelfByIdCustom(Long shelfId);
    @Modifying
    @Query(value = "delete from books_shelves where shelves_id = ?2 and books_id = ?1", nativeQuery = true)
    void deleteBookFromShelf(Long bookId, Long shelfId);
}
