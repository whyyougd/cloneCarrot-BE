package com.hanghea.clonecarrotbe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Column(nullable = false)
    private String title;

//    @Column(nullable = false) // , columnDefinition = "LONGTEXT"
//    private String content;
//
//    @Column(nullable = false)
//    private int price;
//
//    @OneToMany(mappedBy = "post")
//    @Column(nullable = false)
//    private List<Image> imageList;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn
//    private Category category;
//
    @ManyToOne
    @JoinColumn(name="statusid", nullable = false)
    private Status status;

    public Post(String title, User user) {
        this.title = title;
        this.user = user;
    }
    public Post(String title, User user, Status status){
        this.title = title;
        this.user = user;
        this.status = status;
    }

}
