package model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String about;
    private Integer avatarId;

    public User(int userId, String username, String password, String email, String about, int avatarId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.about = about;
        this.avatarId = avatarId;
    }

    public User() {

    }

    public int getId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout(){
        return about;
    }

    public void setAbout(String about){
        this.about = about;
    }

    public void setAvatarId(int avatarId){
        this.avatarId = avatarId;
    }

    public int getAvatarId(){
        return this.avatarId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}