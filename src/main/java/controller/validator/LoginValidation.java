package controller.validator;

import dao.Interfaces.UserDAO;
import model.User;
import services.Hasher;

public class LoginValidation {

    private final String username, password;
    private final UserDAO userDAO;

    public LoginValidation(String username, String password, UserDAO userDAO) {
        this.username = username;
        this.password = password;
        this.userDAO = userDAO;
    }

    public boolean validUsername() {
        User user = userDAO.getUser(username);
        if(user == null){
            return false;
        }
        return true;
    }

    public boolean correctPassword() {
        User user = userDAO.getUser(username);
        String hashedPass = Hasher.generateHash(password);
        if(user == null) {
            return false;
        }
        if(!hashedPass.equals(user.getPassword())){
            return false;
        }
        return true;
    }
}
