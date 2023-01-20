package controller;

import DAO.ConnectionPool;
import Service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(urlPatterns = {
        "/api/games",
        "/api/get_game",
        "/api/add_game",
        "/api/update_game"
})
public class GameController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try(Connection connection = ConnectionPool.getDataSource().getConnection()) {
            GameService gameService = new GameService(connection);
            String id = request.getParameter("id");
            String result;
            if (id == null){
                result = objectMapper.writeValueAsString(gameService.readAll());
            }
            else {
                Game game = gameService.get(Integer.parseInt(id));
                result = objectMapper.writeValueAsString(game);
            }
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(result);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String gameId = request.getParameter("id");
        String name = request.getParameter("name");
        Double price = Double.valueOf(request.getParameter("price"));
        String description = request.getParameter("description");
        int publisherId = Integer.parseInt(request.getParameter("publisher_id"));
        int engineId = Integer.parseInt(request.getParameter("engine_id"));
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            GameService gameService = new GameService(connection);
            if (gameId == null){
                gameService.add(name, price, description, publisherId, engineId);
            }
            else{
                gameService.update(Integer.parseInt(gameId), name,price, description, publisherId, engineId);
            }
            response.setStatus(200);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
