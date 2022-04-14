package virtual.library.vl.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import virtual.library.vl.entity.Author;
import virtual.library.vl.entity.Book;
import virtual.library.vl.entity.BookGenre;
import virtual.library.vl.entity.BookTag;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByName(String name);
    Book findBookByName(String name);
    @Query(value = "select id, description, logo, name, release_date, author_id, book_genre_id from books where id = ?1", nativeQuery = true)
    Book findBookByIdCustom(Long id);
    @Query(value = "select id, description, logo, name, release_date, author_id, book_genre_id from books order by id desc limit 20 offset ?1",nativeQuery = true)
    List<Book> findBooksWithOffset(Long offset);
    @Query(value = "select * from books where lower(name) like lower(concat('%',?1,'%')) order by id desc limit 20 offset ?2", nativeQuery = true)
    List<Book> findBooksByNameContainingIgnoreCase(String name, Long offset);
    @Query(value = "select count(*) from books where lower(name) like lower(concat('%',?1,'%'))", nativeQuery = true)
    Long countFindByName(String name);
    @Query(value = "select id from books join books_shelves on books_id = books.id where shelves_id = ?1 and books_id = ?2", nativeQuery = true)
    Long findBookByShelf(Long shelfId, Long bookId);
    @Query(value = "select * from books left join books_shelves on books_id = books.id where shelves_id = ?1", nativeQuery = true)
    List<Book> getBooksFromShelf(Long shelfId);

    @Modifying
    @Query(value = "delete from books_tags where books_id = ?1", nativeQuery = true)
    void deleteBookTags(Long bookId);
    @Modifying
    @Query(value = "delete from books where id = ?1", nativeQuery = true)
    void deleteBookById(Long bookId);
    @Modifying
    @Query(value = "delete from page where book_id = ?1", nativeQuery = true)
    void deleteBookPages(Long bookId);
}
