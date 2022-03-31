package virtual.library.vl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virtual.library.vl.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByName(String name);
    Book findBookByName(String name);
}
