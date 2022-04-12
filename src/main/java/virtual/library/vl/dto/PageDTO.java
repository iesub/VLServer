package virtual.library.vl.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class PageDTO {
    @Column(name = "id")
    public Long id;
    @Column(name = "page_picture")
    private byte[] pagePicture;
}
