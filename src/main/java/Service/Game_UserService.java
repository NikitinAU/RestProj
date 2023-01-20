package Service;

import DAO.*;
import entity.Game_User;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class Game_UserService {
    public final Game_UserDAO game_userDAO;
    public final GameDAO gameDAO;
    public final UserDAO userDAO;
    public Game_UserService(Connection connection){
        this.game_userDAO = new Game_UserDAO(connection);
        this.gameDAO = new GameDAO(connection);
        this.userDAO = new UserDAO(connection);
    }

    public List<Game_User> readAll(){
        return game_userDAO.readAll();
    }

    public List<Game_User> readByUser(int userId){
        return game_userDAO.readByUser(userId);
    }
    public List<Game_User> readByGame(int gameId){
        return game_userDAO.readByGame(gameId);
    }

    public Game_User add(int gameId, int userId){
        Game_User game_user = Game_User.builder().game(gameDAO.readById(gameId)).user(userDAO.readById(userId)).build();
        int status = game_userDAO.create(game_user);
        if (status == 1){
            game_user = game_userDAO.readLastEntry();
            return game_user;
        }
        return null;
    }

    public Game_User get(int id){
        return game_userDAO.readById(id);
    }

    public Game_User update(int id, int gameId, int userId){
        Game_User updatedGame_User = Game_User.builder().game(gameDAO.readById(gameId)).user(userDAO.readById(userId)).build();
        if (game_userDAO.update(updatedGame_User) == 1){
            return game_userDAO.readById(id);
        }
        return null;
    }

    public Game_User delete(int id){
        Game_User deleted = game_userDAO.readById(id);
        if (game_userDAO.delete(id) == 1){
            return deleted;
        }
        return null;
    }
}
