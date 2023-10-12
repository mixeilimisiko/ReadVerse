package model;

public class Author {
    private int id;
    private String name;
    private String info;
    private String imgPath;

    public Author(int id, String name, String info, String imgPath) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.imgPath = imgPath;
    }

    public Author(String name, String info) {
        this.name = name;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setImgPath(String imgPath){
        this.imgPath = imgPath;
    }

    public String getImgPath(){
        return this.imgPath;
    }
}
