package DAO;

import entity.Engine;
import entity.Game;
import entity.Publisher;
import jdk.jfr.Name;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.List;

public class GameDAOTest extends AbstractDAOTest{
    @Name("Add new Game successfully")
    @Test
    public void addNewGame_success(){
        Game g = Game.builder().id(0).Name("WOW").Price(40.0).Description("Test").publisher(publisherDAO.readById(4)).engine(engineDAO.readById(5)).build();
        Assert.assertEquals(1, gameDAO.create(g));
        Assert.assertEquals(g.getName(), gameDAO.readLastEntry().getName());
    }
    @Name("Get Game by id successfully")
    @Test
    public void getGameById_success() throws PropertyVetoException, SQLException {
        Game g = gameDAO.readById(1);
        Assert.assertEquals(1, g.getId());
    }

    @Test
    @Name("Get Game by id failure")
    public void getGameById_failure(){
        Game g = gameDAO.readById(13);
        Assert.assertEquals(null, g);
    }

    @Test
    @Name("Update one entity successfully")
    public void updateGame_success(){
        Game g = Game.builder().id(1).Name("WOW").Price(40.0).Description("Test").publisher(publisherDAO.readById(4)).engine(engineDAO.readById(5)).build();
        Assert.assertEquals(1, gameDAO.update(g));
        Assert.assertEquals(g.getName(), gameDAO.readById(1).getName());
    }

    @Test
    @Name("Delete one entity")
    public void deleteGame_success(){
        Assert.assertEquals(1, gameDAO.delete(1));
    }

    @Test
    @Name("Read all entities")
    public void readAllGames_success(){
        List<Game> pl = gameDAO.readAll();
        Assert.assertFalse(pl.isEmpty());
        System.out.println(pl.size());
    }
}
