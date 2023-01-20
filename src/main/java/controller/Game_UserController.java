package controller;

import DAO.ConnectionPool;
import Service.Game_UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Game_User;

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
import java.util.List;

@WebServlet(urlPatterns = {
        "/api/libraries",
        "/api/get_library_for_user",
        "/api/get_users_by_game",
        "/api/get_user_game",
        "/api/add_game_user",
        "/api/update_library"
})
public class Game_UserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            Game_UserService game_userService = new Game_UserService(connection);
            String id = request.getParameter("id");
            String gameId = request.getParameter("game_id");
            String userId = request.getParameter("user_id");
            String result;
            if (id != null) {
                Game_User game_user = game_userService.get(Integer.parseInt(id));
                result = objectMapper.writeValueAsString(game_user);
            } else {
                List<Game_User> lgu;
                if (gameId != null) {
                    lgu = game_userService.readByGame(Integer.parseInt(gameId));
                } else if (userId != null) {
                    lgu = game_userService.readByUser(Integer.parseInt(userId));
                } else {
                    lgu = game_userService.readAll();
                }
                result = objectMapper.writeValueAsString(lgu);
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
        String game_userId = request.getParameter("id");
        int gameId = Integer.parseInt(request.getParameter("game_id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));
        Date creationDate = Date.valueOf(request.getParameter("creation_date"));
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            Game_UserService game_userService = new Game_UserService(connection);
            if (game_userId == null){
                game_userService.add(gameId, userId);
            }
            else{
                game_userService.update(Integer.parseInt(game_userId), gameId, userId);
            }
            response.setStatus(200);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
