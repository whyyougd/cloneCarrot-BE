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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false) // , columnDefinition = "LONGTEXT"
    private String content;

    @Column(nullable = false)
    private int price;

//    @OneToMany(mappedBy = "post")
//    @Column(nullable = false)
//    private List<Image> imageList;

//    @OneToMany(mappedBy = "post")
//    private List<Love> loves;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Category category;

//    public Post (String title, List<Image> imageList, String content,
//                 int price, List<Love> loves, Category category){
//        this.title = title;
//        this. imageList = imageList;
//        this.content = content;
//        this.price = price;
//        this.loves = loves;
//        this.category = category;
//    }
    public void update(PostRequestDto postRequestDto, Category category){
        this.title = postRequestDto.getTitle();
//        this.imageList = postRequestDto.getImageList();
        this.content = postRequestDto.getContent();
        this.price = postRequestDto.getPrice();
        this.category = category;
    }

}
