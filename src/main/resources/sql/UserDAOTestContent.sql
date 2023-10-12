USE TestVerse;

INSERT INTO users (user_id, username, password, email, about_user, avatar_id)
VALUES
    (1, 'User1', 'Password1', 'user1@example.com', '', 0),
    (2, 'User2', 'Password2', 'user2@example.com', '', 0),
    (3, 'User3', 'Password3', 'user3@example.com', '', 0),
    (4, 'User4', 'Password4', 'user4@example.com', '', 0),
    (5, 'User5', 'Password5', 'user5@example.com', '', 0);

INSERT INTO followers (follower_id, following_id)
VALUES
    (1, 2),
    (3, 4),
    (5, 1),
    (2, 3),
    (4, 5);



