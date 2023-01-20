package Service;

import DAO.EngineDAO;
import entity.Engine;

import java.sql.Connection;
import java.util.List;

public class EngineService {

    public final EngineDAO engineDAO;

    public EngineService(Connection connection){
        this.engineDAO = new EngineDAO(connection);
    }

    public List<Engine> readAll(){
        return engineDAO.readAll();
    }

    public Engine add(String name){
        Engine engine = Engine.builder().Name(name).build();
        int status = engineDAO.create(engine);
        if (status == 1){
            engine = engineDAO.readLastEntry();
            return engine;
        }
        return null;
    }

    public Engine get(int id){
        return engineDAO.readById(id);
    }

    public Engine update(int id, String name){
        Engine updatedEngine = Engine.builder().id(id).Name(name).build();
        if (engineDAO.update(updatedEngine) == 1){
            return engineDAO.readById(id);
        }
        return null;
    }

    public Engine delete(int id){
        Engine deleted = engineDAO.readById(id);
        if (engineDAO.delete(id) == 1){
            return deleted;
        }
        return null;
    }
}
