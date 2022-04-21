package com.hanghea.clonecarrotbe.domain;

import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
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


    public Post(User user, PostRequestDto postRequestDto, Category category, Status status) {
        this.user = user;
        this.title = postRequestDto.getTitle();
        this.price = postRequestDto.getPrice();
        this.content = postRequestDto.getContent();
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
