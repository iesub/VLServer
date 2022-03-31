package virtual.library.vl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import virtual.library.vl.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByName(String name);
    Book findBookByName(String name);
    @Query(value = "select id, description, logo, name, release_date, author_id, book_genre_id from books order by id desc limit 20 offset ?1",nativeQuery = true)
    List<Book> findBooksWithOffset(Long offset);
}
