package com.backend.login.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="post")
public class Post {

    @Id
    @Column(name="postId")
    private String postId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userId")
    private User user;

    private String title;

    private String content;

    private String image;

    private Date createdDate;

    private Date updatedDate;


}
