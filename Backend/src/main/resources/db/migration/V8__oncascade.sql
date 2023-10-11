DROP TABLE reaction_entries;

CREATE TABLE reaction_entries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    post_id varchar(255) NOT NULL,
    user_id varchar(255) NOT NULL,
    reaction_id INT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (reaction_id) REFERENCES reaction(reaction_id) ON DELETE CASCADE ON UPDATE CASCADE
);
