package com.hanghea.clonecarrotbe.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Love {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loveId;

    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    private Post post;


    @Column(nullable = false)
    private String loveUsername;


    public Love(Post post, String username) {
        this.post = post;
        this.loveUsername = username;
    }
}
