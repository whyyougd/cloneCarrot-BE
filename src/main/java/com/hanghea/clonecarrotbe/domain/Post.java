package com.hanghea.clonecarrotbe.domain;

import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false) // , columnDefinition = "LONGTEXT"
    private String content;

    @Column(nullable = false)
    private Long price;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
//    @Column(nullable = false)
    private List<Image> imageList;

    @OneToMany(mappedBy = "post")
    private List<Love> loves;

    @JoinColumn
    @ManyToOne
    private Category category;

    public Post(PostRequestDto requestDto) {
        super();
    }

//
//    public void update(PostRequestDto postRequestDto, Category category){
//        this.title = postRequestDto.getTitle();
//        this.imageList = postRequestDto.getImageList();
//        this.content = postRequestDto.getContent();
//        this.price = postRequestDto.getPrice();
//        this.category = category;
//    }

}
