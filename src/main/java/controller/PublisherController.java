package controller;

import DAO.ConnectionPool;
import Service.PublisherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Publisher;

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

@WebServlet(urlPatterns = { "/api/publishers",
                            "/api/add_publisher",
                            "/api/get_publisher",
                            "/api/update_publisher" })
public class PublisherController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try(Connection connection = ConnectionPool.getDataSource().getConnection()) {
            PublisherService publisherService = new PublisherService(connection);
            String id = request.getParameter("id");
            String result;
            if (id == null){
                result = objectMapper.writeValueAsString(publisherService.readAll());
            }
            else {
                Publisher publisher = publisherService.get(Integer.parseInt(id));
                result = objectMapper.writeValueAsString(publisher);
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
        String publisherId = request.getParameter("id");
        String name = request.getParameter("name");
        Date creationDate = Date.valueOf(request.getParameter("creation_date"));
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            PublisherService publisherService = new PublisherService(connection);
            if (publisherId == null){
                publisherService.add(name, creationDate);
            }
            else{
                publisherService.update(Integer.parseInt(publisherId), name, creationDate);
            }
            response.setStatus(200);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
