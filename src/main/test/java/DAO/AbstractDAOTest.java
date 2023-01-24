package DAO;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class AbstractDAOTest {
    static ComboPooledDataSource connectionPool;



    static Connection connection;
    static EngineDAO engineDAO;
    static PublisherDAO publisherDAO;
    static GameDAO gameDAO;
    static UserDAO userDAO;
    static Game_UserDAO gameUserDAO;

    @BeforeAll
    public static void init() throws SQLException, PropertyVetoException {

        connectionPool = H2ConnectionPool.getDataSource();
        connection = connectionPool.getConnection();
        connection.setAutoCommit(false);
        connection.commit();
        engineDAO = new EngineDAO(connection);
        publisherDAO = new PublisherDAO(connection);
        gameDAO = new GameDAO(connection);
        userDAO = new UserDAO(connection);
        gameUserDAO = new Game_UserDAO(connection);
    }


    @AfterAll
    public static void close() throws SQLException {
        connection.close();
    }

    @AfterEach
    void rollback() throws SQLException {
        connection.rollback();
    }
}
