package DAO;

import entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class Mapper {

    static final String idField = "gu_id";
    static final String gameIdField = "game_id";
    static final String gameNameField = "game";
    static final String gamePriceField = "price";
    static final String gameDescriptionField = "description";
    static final String userIdField = "user_id";
    static final String usernameField = "username";
    static final String userJoinDateField = "join_date";
    static final String userEmailField = "email";
    static final String publisherIdField = "publisher_id";
    static final String publisherNameField = "publisher";
    static final String publisherCreationDateField = "creation_date";
    static final String engineIdField = "engine_id";
    static final String engineNameField = "engine";

    static Publisher publisherMap(ResultSet resultSet) throws SQLException{
        return Publisher.builder()
                .id(resultSet.getInt(publisherIdField))
                .Name(resultSet.getString(publisherNameField))
                .Creation_Date(resultSet.getDate(publisherCreationDateField))
                .build();
    }

    static Engine engineMap(ResultSet resultSet) throws SQLException{
        return Engine.builder()
                .id(resultSet.getInt(engineIdField))
                .Name(resultSet.getString(engineNameField))
                .build();
    }
    static Game gameMap(ResultSet resultSet) throws SQLException {
        return Game.builder()
                .id(resultSet.getInt(gameIdField))
                .Name(resultSet.getString(gameNameField))
                .Price(resultSet.getInt(gamePriceField))
                .Description(resultSet.getString(gameDescriptionField))
                .publisher(publisherMap(resultSet))
                .engine(engineMap(resultSet))
                .build();
    }

    static User userMap(ResultSet resultSet) throws SQLException{
        return User.builder()
                .id(resultSet.getInt(userIdField))
                .username(resultSet.getString(usernameField))
                .Join_Date(resultSet.getDate(userJoinDateField))
                .email(resultSet.getString(userEmailField))
                .build();
    }

    static Game_User game_userMap(ResultSet resultSet) throws SQLException{
        return Game_User.builder()
                .id(resultSet.getInt(idField))
                .game(gameMap(resultSet))
                .user(userMap(resultSet))
                .build();
    }
}
