package com.hanghea.clonecarrotbe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryid;

    @Column
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<Post>();

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}