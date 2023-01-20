package Service;

import DAO.UserDAO;
import entity.User;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class UserService {
    public final UserDAO userDAO;

    public UserService(Connection connection){
        this.userDAO = new UserDAO(connection);
    }

    public List<User> readAll(){
        return userDAO.readAll();
    }

    public User add(String username, Date join_date, String email){
        User user = User.builder().id(0).username(username).Join_Date(join_date).email(email).build();
        if (userDAO.create(user) == 1){
            return userDAO.readLastEntry();
        }
        return null;
    }

    public User get(int id){
        return userDAO.readById(id);
    }
    public User update(int id, String username, Date join_date, String email){
        User updatedUser = User.builder().id(0).username(username).Join_Date(join_date).email(email).build();
        if (userDAO.update(updatedUser) == 1){
            return userDAO.readById(id);
        }
        return null;
    }

    public User delete(int id){
        User deleted = userDAO.readById(id);
        if (userDAO.delete(id) == 1){
            return deleted;
        }
        return null;
    }
}
