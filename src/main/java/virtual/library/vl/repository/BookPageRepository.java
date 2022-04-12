package virtual.library.vl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import virtual.library.vl.entity.BookPage;

import java.util.List;

public interface BookPageRepository extends JpaRepository<BookPage, Long> {
    @Query(value = "select * from page where book_id = ?1" ,nativeQuery = true)
    public List<BookPage> findByBook(Long id);

    @Query(value = "select count(*) from page where book_id = ?1" ,nativeQuery = true)
    public Long countByBook(Long id);

    @Query(value = "select id, page_picture from page where book_id = ?1 order by id asc limit 1 offset ?2", nativeQuery = true)
    public BookPage getBookPageByNumber(Long bookId, Long offset);
}
