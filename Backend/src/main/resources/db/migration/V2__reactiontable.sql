
CREATE TABLE reaction_entries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    postId varchar(255) NOT NULL,
    userId varchar(255) NOT NULL,
    reactionId INT NOT NULL,
    FOREIGN KEY (postId) REFERENCES post(post_id),
    FOREIGN KEY (userId) REFERENCES user(user_id),
    FOREIGN KEY (reactionId) REFERENCES reaction(reaction_id)
);





