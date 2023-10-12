USE TestVerse;

INSERT INTO users (user_id, username, password, email)
VALUES
    (1, 'User1', 'Password1', 'user1@example.com'),
    (2, 'User2', 'Password2', 'user2@example.com'),
    (3, 'User3', 'Password3', 'user3@example.com'),
    (4, 'User4', 'Password4', 'user4@example.com'),
    (5, 'User5', 'Password5', 'user5@example.com');

INSERT INTO authors (author_name, author_info) VALUES
                                                   ('Author 1', 'Author 1 info'),
                                                   ('Author 2', 'Author 2 info'),
                                                   ('Author 3', 'Author 3 info'),
                                                   ('Author 4', 'Author 4 info'),
                                                   ('Author 5', 'Author 5 info');

-- Insert genres
INSERT INTO genres (name, description) VALUES
                                                  ('Genre 1', 'Genre 1 description'),
                                                  ('Genre 2', 'Genre 2 description'),
                                                  ('Genre 3', 'Genre 3 description'),
                                                  ('Genre 4', 'Genre 4 description'),
                                                  ('Genre 5', 'Genre 5 description');

INSERT INTO books (title, author, description, rating, year)
VALUES
    ('Book 1', 'Author 1', 'Description 1', 4.01, 2001),
    ('Book 2', 'Author 2', 'Description 2', 4.02, 2002),
    ('Book 3', 'Author 3', 'Description 3', 4.03, 2003),
    ('Book 4', 'Author 4', 'Description 4', 4.04, 2004),
    ('Book 5', 'Author 5', 'Description 5', 4.05, 2005);

-- Insert book_genres
INSERT INTO book_genres (book_id, genre)
VALUES
    (1, 'Genre 1'), (1, 'Genre 2'), (1, 'Genre 3'),
    (2, 'Genre 2'), (2, 'Genre 3'), (2, 'Genre 4'),
    (3, 'Genre 1'), (3, 'Genre 3'), (3, 'Genre 5'),
    (4, 'Genre 2'), (4, 'Genre 4'), (4, 'Genre 5'),
    (5, 'Genre 1'), (5, 'Genre 4'), (5, 'Genre 5');


INSERT INTO reviews (review_id, book_id, user_id, rating, comment, review_date)
VALUES
    -- User 1 reviews
    (1, 1, 1, 4.5, 'Great book!', '2023-08-10 14:30:00'),
    (2, 2, 1, 3.8, 'Interesting read.', '2023-08-11 09:15:00'),
    (3, 3, 1, 5.0, 'Highly recommended.', '2023-08-12 18:45:00'),
    (4, 4, 1, 4.2, 'Enjoyed it a lot.', '2023-08-13 11:20:00'),
    (5, 5, 1, 2.5, 'Not my cup of tea.', '2023-08-14 15:10:00'),

    -- User 2 reviews
    (6, 1, 2, 3.7, 'Decent book.', '2023-08-15 12:05:00'),
    (7, 2, 2, 4.8, 'Loved every page.', '2023-08-16 10:30:00'),
    (8, 3, 2, 2.0, 'Disappointing.', '2023-08-17 17:20:00'),
    (9, 4, 2, 4.5, 'Couldnt put it down.', '2023-08-18 14:50:00'),
    (10, 5, 2, 3.3, 'Mixed feelings.', '2023-08-19 16:40:00'),

    -- User 3 reviews
    (11, 1, 3, 4.0, 'Well-written.', '2023-08-20 13:10:00'),
    (12, 2, 3, 3.5, 'Interesting plot twists.', '2023-08-21 08:45:00'),
    (13, 3, 3, 4.7, 'Captivating story.', '2023-08-22 19:30:00'),
    (14, 4, 3, 2.8, 'Expected more.', '2023-08-23 10:25:00'),
    (15, 5, 3, 3.9, 'Solid read.', '2023-08-24 12:15:00'),

    -- User 4 reviews
    (16, 1, 4, 2.2, 'Not impressed.', '2023-08-25 11:40:00'),
    (17, 2, 4, 4.1, 'Kept me engaged.', '2023-08-26 15:55:00'),
    (18, 3, 4, 3.0, 'Average book.', '2023-08-27 09:10:00'),
    (19, 4, 4, 4.6, 'Recommended!', '2023-08-28 17:00:00'),
    (20, 5, 4, 2.9, 'Could have been better.', '2023-08-29 14:20:00'),

    -- User 5 reviews
    (21, 1, 5, 3.8, 'Solid effort.', '2023-08-30 12:30:00'),
    (22, 2, 5, 4.3, 'Impressive storytelling.', '2023-08-31 10:40:00'),
    (23, 3, 5, 2.7, 'Not my genre.', '2023-09-01 18:15:00'),
    (24, 4, 5, 3.5, 'Worth a read.', '2023-09-02 13:05:00'),
    (25, 5, 5, 4.9, 'Absolutely loved it!', '2023-09-03 16:50:00');
