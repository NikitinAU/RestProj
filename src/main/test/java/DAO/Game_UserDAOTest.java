package DAO;

import entity.Game_User;
import jdk.jfr.Name;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.List;

public class Game_UserDAOTest extends AbstractDAOTest{
    @Name("Add new Game_User successfully")
    @Test
    public void addNewGameUser_success(){
        Game_User gu = Game_User.builder().id(0).game(gameDAO.readById(6)).user(userDAO.readById(6)).build();
        Assert.assertEquals(1, gameUserDAO.create(gu));
    }
    @Name("Get Game_User by id successfully")
    @Test
    public void getGameUserById_success() throws PropertyVetoException, SQLException {
        Game_User gu = gameUserDAO.readById(1);
        Assert.assertEquals(1, gu.getId());
    }

    @Test
    @Name("Get Game_User by id failure")
    public void getGameUserById_failure(){
        Game_User gu = gameUserDAO.readById(15);
        Assert.assertEquals(null, gu);
    }

    @Test
    @Name("Update one entity successfully")
    public void updateGameUser_success(){
        Game_User gu = Game_User.builder().id(1).game(gameDAO.readById(6)).user(userDAO.readById(6)).build();
        Assert.assertEquals(1, gameUserDAO.update(gu));
    }

    @Test
    @Name("Delete one entity")
    public void deleteGameUser_success(){
        Assert.assertEquals(1, userDAO.delete(1));
    }

    @Test
    @Name("Read all entities")
    public void readAllGameUsers_success(){
        List<Game_User> pl = gameUserDAO.readAll();
        Assert.assertFalse(pl.isEmpty());
        System.out.println(pl.size());
    }

    @Test
    @Name("Read Games owned by User")
    public void readGamesOwnedByUser_success(){
        List<Game_User> pl = gameUserDAO.readByUser(4);
        Assert.assertFalse(pl.isEmpty());
        System.out.println(pl.size());
    }
    @Test
    @Name("Read Users who own a game")
    public void readGameOwners_success(){
        List<Game_User> pl = gameUserDAO.readByGame(3);
        Assert.assertFalse(pl.isEmpty());
        System.out.println(pl.size());
    }
}
