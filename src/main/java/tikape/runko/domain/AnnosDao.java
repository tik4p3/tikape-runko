/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.Database;


/**
 *
 * @author Joona Niemelä
 */
public class AnnosDao implements Dao<Annos, Integer> {

    private Database database;

    public AnnosDao(Database database) {
        this.database = database;
    }

    @Override
    public Annos findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Annos> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Annos saveOrUpdate(Annos object) throws SQLException {
        
//        try (Connection conn = database.getConnection()) {
//            PreparedStatement stmt = conn.prepareStatement(
//                    "INSERT INTO Annos (nimi, ) VALUES (?, ?)");
//            stmt.setString(1, object.getNimi());
//            stmt.setInt(2, object.());
//            stmt.executeUpdate();
//        }
//
//        return null;
        
        
    }

    @Override
    public void delete(Integer key) throws SQLException {
        
        
    }

}
