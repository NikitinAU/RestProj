package Service;

import DAO.PublisherDAO;
import entity.Publisher;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class PublisherService {
    public final PublisherDAO publisherDAO;

    public PublisherService(Connection connection){
        this.publisherDAO = new PublisherDAO(connection);
    }

    public List<Publisher> readAll(){
        return publisherDAO.readAll();
    }

    public Publisher add(String name, Date creationDate){
        Publisher publisher = Publisher.builder().Name(name).Creation_Date(creationDate).build();
        int status = publisherDAO.create(publisher);
        if (status == 1){
            publisher = publisherDAO.readLastEntry();
            return publisher;
        }
        return null;
    }

    public Publisher get(int id){
        return publisherDAO.readById(id);
    }

    public Publisher update(int id, String name, Date creationDate){
        Publisher updatedPublisher = Publisher.builder().id(id).Name(name).Creation_Date(creationDate).build();
        if (publisherDAO.update(updatedPublisher) == 1){
            return publisherDAO.readById(id);
        }
        return null;
    }

    public Publisher delete(int id){
        Publisher deleted = publisherDAO.readById(id);
        if (publisherDAO.delete(id) == 1){
            return deleted;
        }
        return null;
    }
}
