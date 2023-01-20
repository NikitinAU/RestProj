package DAO;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.beans.PropertyVetoException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionPool {
    private static ComboPooledDataSource cpds;

    public static ComboPooledDataSource getDataSource() throws PropertyVetoException{
        if (cpds == null){
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("org.postgresql.Driver");
            cpds.setJdbcUrl( "jdbc:postgresql://localhost/GamesLibrary" );
            cpds.setUser("postgres");
            cpds.setPassword("zxtkjdtr234");

            cpds.setInitialPoolSize(8);
            cpds.setMinPoolSize(8);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(30);
            cpds.setMaxStatements(100);
        }
        return cpds;
    }
}
