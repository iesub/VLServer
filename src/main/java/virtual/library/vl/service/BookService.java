package virtual.library.vl.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import virtual.library.vl.dto.FiltersDTO;
import virtual.library.vl.dto.PageDTO;
import virtual.library.vl.entity.*;
import virtual.library.vl.repository.*;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookGenreRepository bookGenreRepository;
    @Autowired
    BookPageRepository bookPageRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookTagRepository bookTagRepository;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    public boolean addAuthor(Author author){
        if (authorRepository.existsByName(author.getName())){
            return false;
        } else {
            authorRepository.save(author);
            return true;
        }
    }

    public Author getAuthor(Long id){
        return authorRepository.findById(id).orElse(new Author());
    }

    public List<Author> getAuthors(){
        return authorRepository.findAllByOrderByNameDesc();
    }

    public boolean addBookGenre(BookGenre bookGenre){
        if (bookGenreRepository.existsByName(bookGenre.getName())){
            return false;
        } else {
            bookGenreRepository.save(bookGenre);
            return true;
        }
    }

    public BookGenre getBookGenre(Long id){
        return bookGenreRepository.findById(id).orElse(new BookGenre());
    }

    public List<BookGenre> getBookGenres(){
        return bookGenreRepository.findAllByOrderByNameDesc();
    }

    public boolean addBookTag(BookTag bookTag){
        if (bookTagRepository.existsByName(bookTag.getName())){
            return false;
        } else {
            bookTagRepository.save(bookTag);
            return true;
        }
    }

    public BookTag getBookTag(Long id){
        return bookTagRepository.findById(id).orElse(new BookTag());
    }

    public List<BookTag> getBookTags(){
        return bookTagRepository.findAllByOrderByNameDesc();
    }

    public void addBookPage(BookPage bookPage){
        bookPageRepository.save(bookPage);
    }

    public List<BookPage> getBookPages(Long bookId){
        return bookPageRepository.findByBook(bookId);
    }

    public boolean addBook(Book book){
        if (bookRepository.existsByName(book.getName())){
            return false;
        } else {
            bookRepository.save(book);
            return true;
        }
    }

    public Book getBook(Long id){
        return bookRepository.findBookByIdCustom(id);
    }

    public Book getBook(String name) {return bookRepository.findBookByName(name);}

    public long countBooks(){return bookRepository.count();}

    public void transformPDFToPages(MultipartFile mFile, Book book) throws IOException {
        File file = File.createTempFile("prefix-", "-suffix");
        file.deleteOnExit();
        try {
            mFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PDDocument document = PDDocument.load(file);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
//        for (PDPage pdPage : pdPages) {
//            BufferedImage bim = pdPage.(BufferedImage.TYPE_INT_RGB, 300);
//            ByteArrayOutputStream image = new ByteArrayOutputStream();
//            ImageIO.write(bim, "png", image);
//            byte[] bytes = image.toByteArray();
//            BookPage bookPage = new BookPage();
//            bookPage.setBook(book);
//            bookPage.setPagePicture(bytes);
//            bookPageRepository.save(bookPage);
//        }
        for (int page = 0; page < document.getNumberOfPages(); ++page){
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            ByteArrayOutputStream image = new ByteArrayOutputStream();
            ImageIO.write(bim, "png", image);
            byte[] bytes = image.toByteArray();
            BookPage bookPage = new BookPage();
            bookPage.setBook(book);
            bookPage.setPagePicture(bytes);
            bookPageRepository.save(bookPage);
        }
        file.delete();
    }

    public List<Book> selectBooksWithOffset(Long offset){
        return bookRepository.findBooksWithOffset(offset);
    }

    public List<Book> selectBooksByNameQuery(String name, Long offset){
        return bookRepository.findBooksByNameContainingIgnoreCase(name, offset);}

    public Long countFindBookByName(String name){
        return bookRepository.countFindByName(name);
    }

    public List<Book> selectByFilters(FiltersDTO filtersDTO){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Book> bookCriteria = criteriaBuilder.createQuery(Book.class);

        Root<Book> bookRoot = bookCriteria.from(Book.class);

        bookCriteria.select(bookRoot);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filtersDTO.getAuthor() != null){
            predicates.add(criteriaBuilder.equal(bookRoot.get("author"), filtersDTO.getAuthor()));
        }
        if (filtersDTO.getGenre() != null){
            predicates.add(criteriaBuilder.equal(bookRoot.get("bookGenre"), filtersDTO.getGenre()));
        }
        if (filtersDTO.getTag() != null) {
            Join<BookTag, Book> join = bookRoot.join("tags");
            List<Long> ids = new ArrayList<>();
            for (int i = 0; i < filtersDTO.getTag().size(); i++) {
                ids.add(filtersDTO.getTag().get(i).getId());
            }
            predicates.add(join.in(ids));
        }
        Expression<Long> count = criteriaBuilder.count(bookRoot.get(("id")));
        bookCriteria.where(predicates.toArray(new Predicate[]{}));

        if (filtersDTO.getTag() != null) {
            bookCriteria.groupBy(bookRoot.get("id"));
            bookCriteria.having(criteriaBuilder.equal(count, filtersDTO.getTag().size()));
        }

        List<Order> orderList = new ArrayList<>();
        orderList.add(criteriaBuilder.desc(bookRoot.get("id")));

        List<Book> books = new ArrayList<>();

        bookCriteria.orderBy(orderList);
        try {
            books = entityManager.createQuery(bookCriteria).setFirstResult(Math.toIntExact(filtersDTO.getOffset() - 1)).
                    setMaxResults(20).getResultList();
        } catch (NoResultException nre) {}
        entityManager.close();
        return books;
    }

    public Long countByFilters(FiltersDTO filtersDTO){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> bookCriteria = criteriaBuilder.createQuery(Long.class);

        Root<Book> bookRoot = bookCriteria.from(Book.class);

        bookCriteria.select(criteriaBuilder.count(bookRoot));
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filtersDTO.getAuthor() != null){
            predicates.add(criteriaBuilder.equal(bookRoot.get("author"), filtersDTO.getAuthor()));
        }
        if (filtersDTO.getGenre() != null){
            predicates.add(criteriaBuilder.equal(bookRoot.get("bookGenre"), filtersDTO.getGenre()));
        }
        if (filtersDTO.getTag() != null) {
            Join<BookTag, Book> join = bookRoot.join("tags");
            List<Long> ids = new ArrayList<>();
            for (int i = 0; i < filtersDTO.getTag().size(); i++) {
                ids.add(filtersDTO.getTag().get(i).getId());
            }
            predicates.add(join.in(ids));
        }
        Expression<Long> count = criteriaBuilder.count(bookRoot.get(("id")));
        bookCriteria.where(predicates.toArray(new Predicate[]{}));

        if (filtersDTO.getTag() != null) {
            bookCriteria.groupBy(bookRoot.get("id"));
            bookCriteria.having(criteriaBuilder.equal(count, filtersDTO.getTag().size()));
        }

        Long result = 0L;
        try {
            result = entityManager.createQuery(bookCriteria).getSingleResult();
        } catch (NoResultException nre){

        }
        entityManager.close();
        return result;
    }

    public Long countPagesByBook(Long id){
        return bookPageRepository.countByBook(id);
    }

    public PageDTO getPageByBookAndOffset(Long bookId, Long offset){
        BookPage page = bookPageRepository.getBookPageByNumber(bookId, offset);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPagePicture(page.getPagePicture());
        pageDTO.setId(page.getId());
        return pageDTO;
    }
}
