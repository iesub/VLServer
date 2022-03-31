package virtual.library.vl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import virtual.library.vl.entity.BookPage;

import java.util.List;

public interface BookPageRepository extends JpaRepository<BookPage, Long> {
    @Query(value = "select * from page where book_id = ?1" ,nativeQuery = true)
    public List<BookPage> findByBook(Long id);
}
