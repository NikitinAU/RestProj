package DAO;

import entity.Publisher;
import jdk.jfr.Name;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.beans.PropertyVetoException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class PublisherDAOTest extends AbstractDAOTest {
    @Name("Add new Publisher successfully")
    @Test
    public void addNewPublisher_success(){
        Publisher p = Publisher.builder().id(0).Name("test").Creation_Date(Date.valueOf("1999-11-10")).build();
        Assert.assertEquals(1, publisherDAO.create(p));
        Assert.assertEquals(p.getName(), publisherDAO.readLastEntry().getName());
    }
    @Name("Get Publisher by id successfully")
    @Test
    public void getPublisherById_success() throws PropertyVetoException, SQLException {
        Publisher p = publisherDAO.readById(1);
        Assert.assertEquals(1, p.getId());
    }

    @Test
    @Name("Get Publisher by id failure")
    public void getPublisherById_failure(){
        Publisher p = publisherDAO.readById(13);
        Assert.assertEquals(null, p);
    }

    @Test
    @Name("Update one entity successfully")
    public void updatePublisher_success(){
        Publisher p = Publisher.builder().id(1).Creation_Date(Date.valueOf("1993-11-16")).Name("Sony Sony Sony").build();
        Assert.assertEquals(1, publisherDAO.update(p));
        Assert.assertEquals("Sony Sony Sony", publisherDAO.readById(1).getName());
    }

    @Test
    @Name("Delete one entity")
    public void deletePublisher_success(){
        Assert.assertEquals(1, publisherDAO.delete(1));
    }

    @Test
    @Name("Read all entities")
    public void readAllPublishers_success(){
        List<Publisher> pl = publisherDAO.readAll();
        Assert.assertFalse(pl.isEmpty());
        System.out.println(pl.size());
    }
}
