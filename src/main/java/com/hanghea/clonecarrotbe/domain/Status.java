package com.hanghea.clonecarrotbe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusid;

    @Column
    private String status;

    @OneToMany(mappedBy = "status")
    private List<Post> posts = new ArrayList<Post>();

    public Status(String status) {
        this.status = status;
    }
}
