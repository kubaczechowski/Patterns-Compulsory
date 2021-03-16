package sample.DAL;

import java.sql.Connection;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
/**
 * @author Kuba
 * @date 3/16/2021 11:33 AM
 */
public class DBconnector {
    private SQLServerDataSource dataSource;

    /**
     * In the constructor we initialize the SQLServerDataSource
     * and set initial values
     */
    public DBconnector() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setUser("CSe20B_8");
        dataSource.setPassword("potatoe2021");
        dataSource.setDatabaseName("Facade_Compulsory");
    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }
}
