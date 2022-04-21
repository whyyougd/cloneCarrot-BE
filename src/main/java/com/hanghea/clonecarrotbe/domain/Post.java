package com.hanghea.clonecarrotbe.domain;

import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Getter @Setter
@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false) // , columnDefinition = "LONGTEXT"
    private String content;

    @Column(nullable = false)
    private Long price;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Image> imageList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Love> loves;

    @JoinColumn(name = "categoryid", nullable = false)
    @ManyToOne
    private Category category;

    @ManyToOne
    @JoinColumn(name="statusid", nullable = false)
    private Status status;

    public Post(User user, String title, Long price, String content, Category category, Status status) {
        this.user = user;
        this.title = title;
        this.price = price;
        this.content = content;
        this.category = category;
        this.status = status;
    }



    public void update(PostRequestDto postRequestDto, Category category){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.price = postRequestDto.getPrice();
        this.category = category;
    }

}
