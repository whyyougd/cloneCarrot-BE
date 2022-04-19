package com.hanghea.clonecarrotbe.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageid;

    @Column
    private String imageurl;

    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    private Post post;

    public Image(String eachImage, Post post) {
        this.imageurl = eachImage;
        this.post = post;
    }
}
