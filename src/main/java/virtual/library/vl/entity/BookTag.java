package virtual.library.vl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "book_tag")
public class BookTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = Long.valueOf(0);
    private String name;
    @ManyToMany(mappedBy = "tags")
    @JsonBackReference
    List<Book> books;

    public BookTag() {

    }
}
