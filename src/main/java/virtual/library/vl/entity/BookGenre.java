package virtual.library.vl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "book_genre")
public class BookGenre {@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id = Long.valueOf(0);
    private String name;
    @OneToMany(mappedBy = "bookGenre", fetch = FetchType.LAZY)
    @JsonBackReference
    List<Book> books;

    public BookGenre() {

    }

    public BookGenre(Long id){
        this.id = id;
    }
}
