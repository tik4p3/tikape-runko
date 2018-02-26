package tikape.runko.domain;

import java.sql.*;

public class Database {

    private String databaseAddress;

    
    // pitää laittaa Herokun tietokantajutut vielä              *
    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }
    
    public Database ()  {
        
    }
    
    public static Connection getConnection() throws SQLException{
        
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        
        
        if (dbUrl != null && dbUrl.length() > 0) {
            
            Connection connection = DriverManager.getConnection(dbUrl);
            
            return connection;
        }
        
        Connection connection = DriverManager.getConnection("jdbc:sqlite:kirja.db");

        return connection;
    }
    
}