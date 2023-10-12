package controller.validator;

import dao.Interfaces.UserDAO;
import model.User;

public class RegisterValidation {

    private final String username, email, password, passwordConfirmed;
    private final UserDAO userDAO;

    public RegisterValidation(String username, String password, String passwordConfirmed, String email, UserDAO userDAO) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirmed = passwordConfirmed;
        this.userDAO = userDAO;
    }

    public boolean uniqueUsername() {
        User tempUser = userDAO.getUser(username);
        if(tempUser != null){
            return false;
        }
        return true;
    }

    public boolean uniqueMail() {
        User tempUser = userDAO.getUserByEmail(email);
        if(tempUser != null){
            return false;
        }
        return true;
    }
}
