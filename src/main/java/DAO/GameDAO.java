package DAO;

import entity.Game;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


public class GameDAO implements DAO<Game>{

    private static final Logger log = getLogger(GameDAO.class);

    private final Connection connection;

    public GameDAO(Connection connection){
        this.connection = connection;
    }
    @Override
    public int create(Game entity) {
        int status = 0;
        if (!checkExistByName(entity)) {
            try (PreparedStatement statement = connection.prepareStatement(SQLStatements.ADD.Query)) {
                statement.setString(1, entity.getName());
                statement.setDouble(2, entity.getPrice());
                statement.setString(3, entity.getDescription());
                statement.setInt(4, entity.getPublisher().getId());
                statement.setInt(5, entity.getEngine().getId());
                status = statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return status;
    }

    @Override
    public Game readById(int id) {
        Game g = null;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET.Query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                g = Mapper.gameMap(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return g;
    }

    @Override
    public int update(Game entity) {
        int status = 0;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.UPDATE.Query)) {
            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setString(3, entity.getDescription());
            statement.setInt(4, entity.getPublisher().getId());
            statement.setInt(5, entity.getEngine().getId());
            statement.setInt(6, entity.getId());
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
    public List<Game> readAll() {
        List<Game> lg = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_ALL.Query);
            ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()){
                lg.add(Mapper.gameMap(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lg;
    }

    public boolean checkExistByName(Game entity){
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

    public Game readLastEntry(){
        Game g = null;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_LAST_ENTRY.Query);
            ResultSet resultSet = statement.executeQuery();) {
            while(resultSet.next()){
                g = Mapper.gameMap(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return g;
    }
    private enum SQLStatements{
        GET("""
                select g.Name as Game, g.Price, g.Description, g.id as Game_id,
                g.Publisher_id, p.Name as Publisher, p.Creation_Date,
                g.Engine_id, e.Name as Engine
                from Game as g
                join Publisher as p on g.Publisher_id = p.id
                join Engine as e on g.Engine_id = e.id
                where g.id = ?
                """
        ),
        ADD("""
                insert into Game(Name, Price, Description, Publisher_id, Engine_id) values (?, ?, ?, ?, ?)
                """
        ),
        DELETE("delete from Game where id = ?"),
        UPDATE("""
                update Game set Name = ?, Price = ?, Description = ?, Publisher_id= ?, Engine_id = ? where id = ?
                """
        ),
        GET_NAME("""
                select g.Name as Game, g.Price, g.Description, g.id as Game_id,
                g.Publisher_id, p.Name as Publisher, p.Creation_Date,
                g.Engine_id, e.Name as Engine
                from Game as g
                join Publisher as p on g.Publisher_id = p.id
                join Engine as e on g.Engine_id = e.id
                where g.Name = ?
                """
                ),
        GET_ALL("""
                select g.Name as Game, g.Price, g.Description, g.id as Game_id,
                g.Publisher_id, p.Name as Publisher, p.Creation_Date,
                g.Engine_id, e.Name as Engine
                from Game as g
                join Publisher as p on g.Publisher_id = p.id
                join Engine as e on g.Engine_id = e.id
                """
                ),
        GET_LAST_ENTRY("""
                select TOP 1
                g.Name as Game, g.Price, g.Description, g.id as Game_id,
                g.Publisher_id, p.Name as Publisher, p.Creation_Date,
                g.Engine_id, e.Name as Engine
                from Game as g
                join Publisher as p on g.Publisher_id = p.id
                join Engine as e on g.Engine_id = e.id
                Order By Game_id DESC
                """);

        final String Query;
        SQLStatements(String query){
            this.Query=query;
        }
    }
}
