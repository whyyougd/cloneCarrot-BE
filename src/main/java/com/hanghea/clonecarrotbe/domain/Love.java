package com.hanghea.clonecarrotbe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Love {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likeId;

    @ManyToOne @JoinColumn(name = "postid", nullable = false)
    private Post post;

    @Column(nullable = false)
    private Long likeUserId;


}
