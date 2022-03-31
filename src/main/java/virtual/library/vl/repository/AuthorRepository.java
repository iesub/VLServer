package virtual.library.vl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virtual.library.vl.entity.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByName(String name);
    List<Author> findAllByOrderByNameDesc();
}
