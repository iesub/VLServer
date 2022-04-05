package virtual.library.vl.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import virtual.library.vl.entity.*;
import virtual.library.vl.repository.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
        return bookRepository.getById(id);
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
        List<PDPage> pdPages = document.getDocumentCatalog().getAllPages();
        for (PDPage pdPage : pdPages) {
            BufferedImage bim = pdPage.convertToImage(BufferedImage.TYPE_INT_RGB, 300);
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
}
