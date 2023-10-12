package model;

public class Genre {

    private int id;
    private String name;
    private String description;


    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Genre(int id, String name, String description) {
        this(name, description);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
