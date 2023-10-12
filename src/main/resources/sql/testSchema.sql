-- Select the database

CREATE SCHEMA IF NOT EXISTS TestVerse;
USE TestVerse;

-- Drop tables if they exist
DROP TABLE IF EXISTS ListBooks;
DROP TABLE IF EXISTS BookLists;
DROP TABLE IF EXISTS followers;
DROP TABLE IF EXISTS recommendations;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS book_genres;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS avatars;


-- Create User Table
CREATE TABLE users
(
    user_id  INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    about_user VARCHAR(255) DEFAULT '' NOT NULL,
    avatar_id INT DEFAULT 0 NOT NULL
);

CREATE TABLE avatars
(
    avatar_id INT,
    img_path VARCHAR(255) NOT NULL

);

-- Create Authors Table
CREATE TABLE authors
(
    author_id     INT PRIMARY KEY AUTO_INCREMENT,
    author_name   VARCHAR(255) NOT NULL,
    author_info   TEXT,
    img_path TEXT
    -- Additional author information columns
);

CREATE TABLE genres (
                        genre_id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255) NOT NULL,
                        description TEXT

);


-- Create Book Table
CREATE TABLE books
(
    book_id     INT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(255) NOT NULL,
    description TEXT,
    rating      DECIMAL(3, 2),
    year        INT,
    cover_path VARCHAR(255)
    -- Additional book information columns
);

-- Create Book Genres Table
CREATE TABLE book_genres (
                             book_id INT,
                             genre VARCHAR(255)
    --                       FOREIGN KEY (book_id) REFERENCES books (book_id)
    --                       FOREIGN KEY (genre) REFERENCES genres (name)
);



-- Create Review Table
CREATE TABLE reviews
(
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id   INT,
    user_id   INT,
    rating    DECIMAL(3, 2),
    comment   TEXT,
    review_date DATETIME,
    FOREIGN KEY (book_id) REFERENCES books (book_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);



-- Create Favorite Table
CREATE TABLE favorites
(
    favorite_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id     INT,
    book_id     INT,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (book_id) REFERENCES books (book_id)
);

-- Create Recommendation Table
CREATE TABLE recommendations
(
    recommendation_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id           INT,
    book_id           INT,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (book_id) REFERENCES books (book_id)
);

CREATE TABLE followers
(
    follower_id INT NOT NULL,
    following_id INT NOT NULL,
    PRIMARY KEY (follower_id, following_id),
    FOREIGN KEY (follower_id) REFERENCES users(user_id),
    FOREIGN KEY (following_id) REFERENCES users(user_id)
);

-- Create BookLists table
CREATE TABLE BookLists (
                           list_id INT PRIMARY KEY AUTO_INCREMENT,
                           title VARCHAR(255) NOT NULL,
                           description TEXT,
                           user_id INT,
                           FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create ListBooks table with composite primary key
CREATE TABLE ListBooks (
                           list_id INT,
                           book_id INT,
                           PRIMARY KEY (list_id, book_id),
                           FOREIGN KEY (list_id) REFERENCES BookLists(list_id),
                           FOREIGN KEY (book_id) REFERENCES books(book_id)
);
