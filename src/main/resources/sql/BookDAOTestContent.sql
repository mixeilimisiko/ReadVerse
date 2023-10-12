USE TestVerse;



-- Insert authors
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
    ('Book 5', 'Author 5', 'Description 5', 4.05, 2005),
    ('Book 6', 'Author 1', 'Description 6', 4.06, 2006),
    ('Book 7', 'Author 2', 'Description 7', 4.07, 2007),
    ('Book 8', 'Author 3', 'Description 8', 4.08, 2008),
    ('Book 9', 'Author 4', 'Description 9', 4.09, 2009),
    ('Book 10', 'Author 5', 'Description 10', 4.1, 2010);

-- Insert book_genres
INSERT INTO book_genres (book_id, genre)
VALUES
    (1, 'Genre 1'), (1, 'Genre 2'), (1, 'Genre 3'),
    (2, 'Genre 2'), (2, 'Genre 3'), (2, 'Genre 4'),
    (3, 'Genre 1'), (3, 'Genre 3'), (3, 'Genre 5'),
    (4, 'Genre 2'), (4, 'Genre 4'), (4, 'Genre 5'),
    (5, 'Genre 1'), (5, 'Genre 4'), (5, 'Genre 5'),
    (6, 'Genre 1'), (6, 'Genre 2'), (6, 'Genre 3'),
    (7, 'Genre 1'), (7, 'Genre 3'), (7, 'Genre 4'),
    (8, 'Genre 2'), (8, 'Genre 3'), (8, 'Genre 5'),
    (9, 'Genre 1'), (9, 'Genre 4'), (9, 'Genre 5'),
    (10, 'Genre 2'), (10, 'Genre 3'), (10, 'Genre 4');

