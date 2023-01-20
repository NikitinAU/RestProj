package DAO;

import entity.Engine;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;

import static java.rmi.server.LogStream.log;
import static org.slf4j.LoggerFactory.getLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EngineDAO implements DAO<Engine> {
    private static final Logger log = getLogger(EngineDAO.class);

    private final Connection connection;

    public EngineDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public int create(Engine entity) {
        int status = 0;
        if (!checkExistByName(entity)) {
            log("Entity {} does not exist: creating");
            try (PreparedStatement statement = connection.prepareStatement(SQLStatements.ADD.Query)) {
                statement.setString(1, entity.getName());
                log("Attempting to create entity {}");
                status = statement.executeUpdate();
                log("Success! Created Entity {}");
            } catch (SQLException e) {
                log("Error in creating Entity: SQL error");
                try {
                    log("Attempting to rollback");
                    connection.rollback();
                    log("Rollback successful");
                } catch (SQLException ex) {
                    log("Rollback failed!");
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            }
        }
        else log("Entity {} already exists within Database!");
        return status;
    }
    @Override
    public Engine readById(int id) {
        Engine engine = null;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET.Query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                engine = Mapper.engineMap(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return engine;
    }

    @Override
    public int update(Engine entity) {
        int status = 0;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.UPDATE.Query)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getId());
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
    public List<Engine> readAll() {
        List<Engine> le = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_ALL.Query);
            ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()){
                le.add(Mapper.engineMap(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return le;
    }

    public boolean checkExistByName(Engine entity){
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

    public Engine readLastEntry(){
        Engine e = null;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_LAST_ENTRY.Query);
            ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()){
                e = Mapper.engineMap(resultSet);
            }
        return e;
    } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private enum SQLStatements {
        GET("select id as Engine_id, Name as Engine from Engine where id = ?"),
        ADD("insert into Engine(Name) values (?)"),
        DELETE("delete from Engine where id = ?"),
        UPDATE("update Engine set Name = ? where id = ?"),
        GET_ALL("select id as Engine_id, Name as Engine from Engine"),
        GET_NAME("select * from Engine where Name = ?"),
        GET_LAST_ENTRY("SELECT TOP 1 id as Engine_id, Name as Engine FROM Engine ORDER BY ID DESC");

        final String Query;
        SQLStatements(String query){
            this.Query = query;
        }
    }
}
