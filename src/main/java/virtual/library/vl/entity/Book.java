package virtual.library.vl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = Long.valueOf(0);
    private String name;
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] logo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    @Column(columnDefinition="TEXT")
    private String description;
    @ManyToOne
    @JsonManagedReference
    Author author;
    @ManyToOne
    @JsonManagedReference
    BookGenre bookGenre;
    @ManyToMany(fetch = FetchType.EAGER)
    List<BookTag> tags = new ArrayList<>();
    @JsonBackReference
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    List<BookPage> pages;

    public void addTag(BookTag bookTag){
        if (!tags.contains(bookTag)) {
            tags.add(bookTag);
        }
    }
}
