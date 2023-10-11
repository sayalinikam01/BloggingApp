package com.backend.login.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="reaction_entries")
public class Reaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "post_id")
    private String postId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "reaction_id")
    private int reactionId;

    public Reaction(String postId, String userId, int reactionId) {
        this.postId = postId;
        this.userId = userId;
        this.reactionId = reactionId;
    }




}
