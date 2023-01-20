package DAO;

import entity.Engine;
import jdk.jfr.Name;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;

import java.sql.SQLException;
import java.util.List;

public class EngineDAOTest extends AbstractDAOTest{

    @Name("Add new Engine successfully")
    @Test
    public void addNewEngine_success(){
        Engine e = Engine.builder().id(1).Name("test").build();
        Assert.assertEquals(1, engineDAO.create(e));
        Assert.assertEquals(e.getName(), engineDAO.readLastEntry().getName());
    }
    @Name("Get Engine by id successfully")
    @Test
    public void getEngineById_success() throws PropertyVetoException, SQLException {
        Engine e = engineDAO.readById(1);
        Assert.assertEquals(1, e.getId());
    }

    @Test
    @Name("Get Engine by id failure")
    public void getEngineById_failure(){
        Engine e = engineDAO.readById(13);
        Assert.assertEquals(null, e);
    }

    @Test
    @Name("Update one entity successfully")
    public void updateEngine_success(){
        Engine e = Engine.builder().id(1).Name("test").build();
        Assert.assertEquals(1, engineDAO.update(e));
        Assert.assertEquals(e.getName(), engineDAO.readById(1).getName());
    }

    @Test
    @Name("Delete one entity")
    public void deleteEngine_success(){
        Assert.assertEquals(1, engineDAO.delete(1));
    }

    @Test
    @Name("Read all entities")
    public void readAllEngines_success(){
        List<Engine> pl = engineDAO.readAll();
        Assert.assertFalse(pl.isEmpty());
        System.out.println(pl.size());
    }
}
