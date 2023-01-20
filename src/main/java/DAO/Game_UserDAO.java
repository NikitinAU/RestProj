package DAO;

import entity.Game_User;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class Game_UserDAO implements DAO<Game_User> {

    public static final Logger log = getLogger(Game_UserDAO.class);

    private final Connection connection;

    public Game_UserDAO(Connection connection){
        this.connection = connection;
    }
    @Override
    public int create(Game_User entity) {
        int status = 0;
        if (!checkExistGameInUser(entity)) {
            try (PreparedStatement statement = connection.prepareStatement(SQLStatements.ADD.Query)) {
                statement.setInt(1, entity.getGame().getId());
                statement.setInt(2, entity.getUser().getId());

                status = statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return status;
    }

    @Override
    public Game_User readById(int id) {
        Game_User gu = null;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET.Query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                gu = Mapper.game_userMap(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gu;
    }

    @Override
    public int update(Game_User entity) {
        int status = 0;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.UPDATE.Query)) {
            statement.setInt(1, entity.getGame().getId());
            statement.setInt(2, entity.getUser().getId());
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
    public List<Game_User> readAll() {
        List<Game_User> lgu = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_ALL.Query);
            ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()){
                lgu.add(Mapper.game_userMap(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lgu;
    }

    public List<Game_User> readByGame(int gameId){
        List<Game_User> lgu = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_BY_GAME.Query)) {
            statement.setInt(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                lgu.add(Mapper.game_userMap(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lgu;
    }
    public List<Game_User> readByUser(int userId){
        List<Game_User> lgu = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_BY_USER.Query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                lgu.add(Mapper.game_userMap(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lgu;
    }
    public boolean checkExistGameInUser(Game_User entity){
        boolean doesExist = false;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_PAIR.Query)){
            statement.setInt(1, entity.getGame().getId());
            statement.setInt(2, entity.getUser().getId());
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

    public Game_User readLastEntry(){
        Game_User gu = null;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET.Query);
            ResultSet resultSet = statement.executeQuery();) {
            while(resultSet.next()){
                gu = Mapper.game_userMap(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gu;
    }
    private enum SQLStatements{
        GET("""
                select
                gu.id as GU_id,
                g.id as Game_id, g.Name as Game, g.Price, g.Description,
                g.Publisher_id, p.Name as Publisher, p.Creation_Date,
                g.Engine_id, e.Name as Engine,
                u.id as User_id, u.username, u.Join_Date, u.email from Game_User as gu
                join Game as g on gu.Game_id = g.id
                join \"USER\" as u on gu.User_id = u.id
                join Publisher as p on g.Publisher_id = p.id
                join Engine as e on g.Engine_id = e.id
                where gu.id = ?
                """
                ),
        ADD("insert into Game_User(Game_id, User_id) values (?, ?)"),
        DELETE("delete from Game_User where id = ?"),
        UPDATE("update Game_User set Game_id = ?, User_id = ? where id = ?"),
        GET_PAIR("""
                select
                gu.id as GU_id,
                g.id as Game_id, g.Name as Game, g.Price, g.Description,
                g.Publisher_id, p.Name as Publisher, p.Creation_Date,
                g.Engine_id, e.Name as Engine,
                u.id as User_id, u.username, u.Join_Date, u.email from Game_User as gu
                join Game as g on gu.Game_id = g.id
                join \"USER\" as u on gu.User_id = u.id
                join Publisher as p on g.Publisher_id = p.id
                join Engine as e on g.Engine_id = e.id
                where g.id = ? and u.id = ?
                """
                ),
        GET_ALL("""
                select
                gu.id as GU_id,
                g.id as Game_id, g.Name as Game, g.Price, g.Description,
                g.Publisher_id, p.Name as Publisher, p.Creation_Date,
                g.Engine_id, e.Name as Engine,
                u.id as User_id, u.username, u.Join_Date, u.email from Game_User as gu
                join Game as g on gu.Game_id = g.id
                join \"USER\" as u on gu.User_id = u.id
                join Publisher as p on g.Publisher_id = p.id
                join Engine as e on g.Engine_id = e.id
                """
                ),
        GET_LAST_ENTRY("""
                select TOP 1
                gu.id as GU_id,
                g.id as Game_id, g.Name as Game, g.Price, g.Description,
                g.Publisher_id, p.Name as Publisher, p.Creation_Date,
                g.Engine_id, e.Name as Engine,
                u.id as User_id, u.username, u.Join_Date, u.email from Game_User as gu
                join Game as g on gu.Game_id = g.id
                join \"USER\" as u on gu.User_id = u.id
                join Publisher as p on g.Publisher_id = p.id
                join Engine as e on g.Engine_id = e.id
                ORDER BY id desc
                """
        ),
        GET_BY_USER(
                """
                select
                gu.id as GU_id,
                g.id as Game_id, g.Name as Game, g.Price, g.Description,
                g.Publisher_id, p.Name as Publisher, p.Creation_Date,
                g.Engine_id, e.Name as Engine,
                u.id as User_id, u.username, u.Join_Date, u.email from Game_User as gu
                join Game as g on gu.Game_id = g.id
                join \"USER\" as u on gu.User_id = u.id
                join Publisher as p on g.Publisher_id = p.id
                join Engine as e on g.Engine_id = e.id
                where User_id = ?
                """
        ),
        GET_BY_GAME(
                """
                select
                gu.id as GU_id,
                g.id as Game_id, g.Name as Game, g.Price, g.Description,
                g.Publisher_id, p.Name as Publisher, p.Creation_Date,
                g.Engine_id, e.Name as Engine,
                u.id as User_id, u.username, u.Join_Date, u.email from Game_User as gu
                join Game as g on gu.Game_id = g.id
                join \"USER\" as u on gu.User_id = u.id
                join Publisher as p on g.Publisher_id = p.id
                join Engine as e on g.Engine_id = e.id
                where Game_id = ?
                """
        );



        final String Query;
        SQLStatements(String query){
            this.Query=query;
        }
    }

}
