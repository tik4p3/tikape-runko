package tikape.runko.domain;

import java.sql.*;

public class Database {

    private String databaseAddress;

    
    // pitää laittaa Herokun tietokantajutut vielä              *
    public Database() throws ClassNotFoundException {
        this.databaseAddress = "jotain";
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
    
}