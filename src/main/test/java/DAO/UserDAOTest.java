package DAO;

import entity.User;
import jdk.jfr.Name;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class UserDAOTest extends AbstractDAOTest{
    @Name("Add new User successfully")
    @Test
    public void addNewUser_success(){
        User u = User.builder().id(0).username("test").Join_Date(Date.valueOf("2000-01-01")).email("test.test").build();
        Assert.assertEquals(1, userDAO.create(u));
        Assert.assertEquals(u.getUsername(), userDAO.readLastEntry().getUsername());
    }
    @Name("Get User by id successfully")
    @Test
    public void getUserById_success() throws PropertyVetoException, SQLException {
        User u = userDAO.readById(1);
        Assert.assertEquals(1, u.getId());
    }

    @Test
    @Name("Get User by id failure")
    public void getUserById_failure(){
        User u = userDAO.readById(15);
        Assert.assertEquals(null, u);
    }

    @Test
    @Name("Update one entity successfully")
    public void updateUser_success(){
        User u = User.builder().id(1).username("test").Join_Date(Date.valueOf("2000-01-01")).email("test.test").build();
        Assert.assertEquals(1, userDAO.update(u));
        Assert.assertEquals(u.getUsername(), userDAO.readById(1).getUsername());
    }

    @Test
    @Name("Delete one entity")
    public void deleteUser_success(){
        Assert.assertEquals(1, userDAO.delete(1));
    }

    @Test
    @Name("Read all entities")
    public void readAllUsers_success(){
        List<User> pl = userDAO.readAll();
        Assert.assertFalse(pl.isEmpty());
        System.out.println(pl.size());
    }
}
