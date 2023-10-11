package com.backend.login.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name="post")
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @Column(name="postId")
    private String postId;

    @ManyToOne()
    @JoinColumn(name="userId")
    private User user;

    private String title;
    @Column(length = 1000)
    private String content;

    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Timestamp createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Timestamp updatedDate;


}
