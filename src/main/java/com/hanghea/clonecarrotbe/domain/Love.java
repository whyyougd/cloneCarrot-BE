package com.hanghea.clonecarrotbe.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Love {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loveId;

    @ManyToOne @JoinColumn(name = "postid", nullable = false)
    private Main main;

    @Column(nullable = false)
    private String loveUsername;


    public Love(Main main, String username) {
        this.main = main;
        this.loveUsername = username;
    }
}
