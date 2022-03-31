package virtual.library.vl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virtual.library.vl.entity.Author;
import virtual.library.vl.entity.BookGenre;

import java.util.List;

public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {
    boolean existsByName(String name);
    List<BookGenre> findAllByOrderByNameDesc();
}
