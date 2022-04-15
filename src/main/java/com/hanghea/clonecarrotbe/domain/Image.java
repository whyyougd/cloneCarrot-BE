package com.hanghea.clonecarrotbe.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageid;

    @Column
    private String imageurl;

    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    private Post post;
}
