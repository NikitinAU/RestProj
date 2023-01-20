package Service;

import DAO.EngineDAO;
import DAO.GameDAO;
import DAO.PublisherDAO;
import entity.Game;

import java.sql.Connection;
import java.util.List;

public class GameService {
    public final GameDAO gameDAO;
    public final PublisherDAO publisherDAO;
    public final EngineDAO engineDAO;

    public GameService(Connection connection){
        this.gameDAO = new GameDAO(connection);
        this.publisherDAO = new PublisherDAO(connection);
        this.engineDAO = new EngineDAO(connection);
    }

    public List<Game> readAll(){
        return gameDAO.readAll();
    }

    public Game add(String name, Double price, String description, int p_id, int e_id){
        Game game = Game.builder().id(0).Name(name).Price(price).Description(description).
                publisher(publisherDAO.readById(p_id)).
                engine(engineDAO.readById(e_id)).build();
        int status = gameDAO.create(game);
        if (status == 1){
            game = gameDAO.readLastEntry();
            return game;
        }
        return null;
    }

    public Game get(int id){
        return gameDAO.readById(id);
    }

    public Game update(int id, String name, Double price, String description, int p_id, int e_id){
        Game updatedGame = Game.builder().id(id).Name(name).Price(price).Description(description).
                publisher(publisherDAO.readById(p_id)).
                engine(engineDAO.readById(e_id)).build();
        if (gameDAO.update(updatedGame) == 1){
            return gameDAO.readById(id);
        }
        return null;
    }

    public Game delete(int id){
        Game deleted = gameDAO.readById(id);
        if (gameDAO.delete(id) == 1){
            return deleted;
        }
        return null;
    }
}
