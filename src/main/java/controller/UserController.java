package controller;

import DAO.ConnectionPool;
import Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;

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
        "/api/users",
        "/api/add_user",
        "/api/update_user",
        "/api/get_user"
})
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try(Connection connection = ConnectionPool.getDataSource().getConnection()) {
            UserService userService = new UserService(connection);
            String id = request.getParameter("id");
            String result;
            if (id == null){
                result = objectMapper.writeValueAsString(userService.readAll());
            }
            else {
                User user = userService.get(Integer.parseInt(id));
                result = objectMapper.writeValueAsString(user);
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
        String userId = request.getParameter("id");
        String name = request.getParameter("username");
        Date joinDate = Date.valueOf(request.getParameter("join_date"));
        String email = request.getParameter("email");
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            UserService userService = new UserService(connection);
            if (userId == null){
                userService.add(name, joinDate, email);
            }
            else{
                userService.update(Integer.parseInt(userId), name, joinDate, email);
            }
            response.setStatus(200);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
