package controller;

import DAO.ConnectionPool;
import Service.EngineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Engine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = { "/api/engines",
                            "/api/get_engine",
                            "/api/add_engine",
                            "/api/update_engine" })
public class EngineController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        try(Connection connection = ConnectionPool.getDataSource().getConnection()) {
            EngineService engineService = new EngineService(connection);
            String id = request.getParameter("id");
            String result;
            if (id == null){
                result = objectMapper.writeValueAsString(engineService.readAll());
            }
            else{
                Engine engine = engineService.get(Integer.parseInt(id));
                result = objectMapper.writeValueAsString(engine);
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
        String engineId = request.getParameter("id");
        String name = request.getParameter("name");
        try(Connection connection = ConnectionPool.getDataSource().getConnection()) {
            EngineService engineService = new EngineService(connection);
            if (engineId == null){
                engineService.add(name);
            }
            else{
                engineService.update(Integer.parseInt(engineId), name);
            }
            response.setStatus(200);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
