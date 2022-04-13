package virtual.library.vl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "shelf")
public class BookShelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = Long.valueOf(0);
    private String name;
    private int bookCount = 0;
    @ManyToOne()
    @JsonBackReference
    User owner;
    @JsonBackReference
    @ManyToMany(mappedBy = "shelves")
    List<Book> books;
}
