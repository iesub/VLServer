package virtual.library.vl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virtual.library.vl.entity.BookGenre;
import virtual.library.vl.entity.BookTag;

import java.util.List;

public interface BookTagRepository extends JpaRepository<BookTag, Long> {
    boolean existsByName(String name);
    List<BookTag> findAllByOrderByNameDesc();
}
