package DAO;

import entity.User;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class UserDAO implements DAO<User> {
    private static final Logger log = getLogger(UserDAO.class);

    private final Connection connection;

    public UserDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public int create(User entity){
        int status = 0;
        if (!checkExistByName(entity)) {
            try (PreparedStatement statement = connection.prepareStatement(SQLStatements.ADD.Query);) {

                statement.setString(1, entity.getUsername());
                statement.setString(2, entity.getJoin_Date().toString());
                statement.setString(3, entity.getEmail());

                status = statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return status;
    }

    @Override
    public User readById(int id) {
        User u = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLStatements.GET.Query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                 u = Mapper.userMap(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return u;
    }

    @Override
    public int update(User u){
        int status = 0;

        try (PreparedStatement statement = connection.prepareStatement(SQLStatements.UPDATE.Query)){
            statement.setString(1, u.getUsername());
            statement.setString(2,u.getJoin_Date().toString());
            statement.setString(3,u.getEmail());
            statement.setInt(4, u.getId());

            status = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return status;
    }

    @Override
    public int delete(int id){
        int status = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLStatements.DELETE.Query)) {
            statement.setInt(1, id);
            status = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    @Override
    public List<User> readAll() {
        List<User> lu = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_ALL.Query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()){
                lu.add(Mapper.userMap(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lu;
    }

    public boolean checkExistByName(User entity){
        boolean doesExist = false;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_NAME.Query)){
            statement.setString(1, entity.getUsername());
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

    public User readLastEntry(){
        User u = null;
        try(PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_LAST_ENTRY.Query);
            ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()){
                u = Mapper.userMap(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return u;
    }
    private enum SQLStatements {
        GET("select id as User_id, username, Join_Date, email from \"USER\" where id = ?"),
        ADD("insert into \"USER\"(username,Join_Date,email) values (?,?,?)"),
        DELETE("DELETE FROM \"USER\" WHERE id = ?"),
        UPDATE("update \"USER\" set username = ?,Join_Date = ?,email = ? where id = ?"),
        GET_NAME("select * from \"USER\" where username = ?"),
        GET_ALL("select id as User_id, username, Join_Date, email from \"USER\""),
        GET_LAST_ENTRY("select TOP 1 id as User_id, username, Join_Date, email from \"USER\" Order by User_id DESC");

        final String Query;
        SQLStatements(String query){
            this.Query = query;
        }
    }
}
