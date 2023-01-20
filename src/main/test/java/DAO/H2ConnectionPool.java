package DAO;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;

public class H2ConnectionPool {
    private static ComboPooledDataSource cpds;

    public static ComboPooledDataSource getDataSource() throws PropertyVetoException {
        if (cpds == null){
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("org.h2.Driver");
            cpds.setJdbcUrl( Driver.H2_TEST.getUrl() );
            cpds.setUser("sa");
            cpds.setPassword("");

            cpds.setInitialPoolSize(1);
            cpds.setMinPoolSize(1);
            cpds.setAcquireIncrement(1);
            cpds.setMaxPoolSize(30);
            cpds.setMaxStatements(100);
        }
        return cpds;
    }
}
