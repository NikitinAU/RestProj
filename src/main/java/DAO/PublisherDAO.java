package DAO;

import entity.Publisher;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Getter
@Setter
public class PublisherDAO implements DAO<Publisher>{

    private static final Logger log = getLogger(PublisherDAO.class);
    private final Connection connection;

    public PublisherDAO(Connection connection){
        this.connection = connection;
    }
    @Override
    public int create(Publisher entity) {
        int status = 0;
        if (!checkExistByName(entity)) {
            try (PreparedStatement statement = connection.prepareStatement(SQLStatements.ADD.Query)) {
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getCreation_Date().toString());
                status = statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return status;
    }

    @Override
    public Publisher readById(int id) {
        Publisher p = null;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET.Query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                p = Mapper.publisherMap(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    @Override
    public int update(Publisher entity) {
        int status = 0;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.UPDATE.Query)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCreation_Date().toString());
            statement.setInt(3, entity.getId());
            status = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    @Override
    public int delete(int id) {
        int status = 0;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.DELETE.Query)) {
            statement.setInt(1, id);
            status = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    @Override
    public List<Publisher> readAll() {
        List<Publisher> le = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_ALL.Query);
            ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()){
                le.add(Mapper.publisherMap(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return le;
    }

    public boolean checkExistByName(Publisher entity){
        boolean doesExist = false;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_NAME.Query)){
            statement.setString(1, entity.getName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                doesExist = true;
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doesExist;
    }

    public Publisher readLastEntry(){
        Publisher p = null;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_LAST_ENTRY.Query);
            ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()){
                p = Mapper.publisherMap(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
    private enum SQLStatements{
        GET("select id as Publisher_id, Name as Publisher, Creation_Date from Publisher where id = ?"),
        ADD("insert into Publisher(Name, Creation_Date) values (?, ?)"),
        DELETE("delete from Publisher where id = ?"),
        UPDATE("update Publisher set Name = ?, Creation_Date = ? where id = ?"),
        GET_NAME("select * from Publisher where Name = ?"),
        GET_ALL("select id as Publisher_id, Name as Publisher, Creation_Date from Publisher"),
        GET_LAST_ENTRY("select TOP 1 id as Publisher_id, Name as Publisher, Creation_Date from Publisher ORDER BY id DESC");

        final String Query;
        SQLStatements(String query){
            this.Query=query;
        }
    }
}
