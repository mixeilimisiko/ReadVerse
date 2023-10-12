package model;

import java.util.List;
import java.util.Objects;

public class Book {

    private int id;
    private String title;
    private String author;
    private String description;
    private double rating;
    private List<String> genres;
    private int year;
    private String bookCoverPath;


    // Constructor
    public Book(String title, String author, String description, double rating, List<String> genres, int year) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.genres = genres;
        this.year = year;
        this.bookCoverPath = "noCover.png";
    }

    public Book(int id, String title, String author, String description, double rating, List<String> genres, int year) {
        this(title, author, description, rating, genres, year);
        this.id = id;
    }

    public Book(int id, String title, String author, String description, double rating, List<String> genres, int year, String bookCoverPath) {
        this(id, title, author, description, rating, genres, year);
        this.bookCoverPath = bookCoverPath;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getBookcoverPath() {
        return bookCoverPath;
    }

    public String getFullPath(){
        return "/bookCovers/"+bookCoverPath;
    }

    public void setBookcoverPath(String bookcoverPath) {
        this.bookCoverPath = bookcoverPath;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book other = (Book) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
