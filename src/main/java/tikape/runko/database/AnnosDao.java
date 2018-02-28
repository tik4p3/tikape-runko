package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Annos;
import tikape.runko.domain.Database;
import tikape.runko.domain.Raakaaine;

public class AnnosDao implements Dao<Annos, Integer> {

    private Database database;

    public AnnosDao(Database database) {
        this.database = database;
    }

    @Override
    public Annos findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Annos annos = new Annos(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();
        rs.close();
        conn.close();

        return annos;
    }

    @Override
    public List<Annos> findAll() throws SQLException {

        List<Annos> annokset = new ArrayList<>();;

        try (Connection connection = database.getConnection()) {
            

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Annos");
            ResultSet rs = stmt.executeQuery();
            
            System.out.println("AnnosDao findAll suoritti kyselyn " + rs.toString());
            
            while (rs.next()) {

                Annos annos = new Annos(rs.getInt("id"), rs.getString("nimi"));
                annokset.add(annos);

            }
            
            System.out.println("(AnnosDao) Onko Javaan luotu lista tyhjä: " + annokset.isEmpty());
            for (Annos annos : annokset)   {
                System.out.println(" (AnnosDao) " + annos.getId() + "|" + annos.getNimi());
            }
            
            
            stmt.close();
            rs.close();
            connection.close();
        }

        return annokset;

    }

    @Override
    public Annos saveOrUpdate(Annos object) throws SQLException {

        try (Connection conn = database.getConnection()) {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos (id, nimi) VALUES (?, ?)");
        stmt.setInt(1, object.getId());
        stmt.setString(2, object.getNimi());
        stmt.executeUpdate();
        }

//        Samanlainen kuin RaakaaineDaon metodi
//        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos (nimi) VALUES (?)");
//        stmt.setString(1, object.getNimi());
//        stmt.executeUpdate();
//        stmt.close();
//
//        PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM Annos WHERE nimi = ?");
//        stmt2.setString(1, object.getNimi());
//        ResultSet rs = stmt2.executeQuery();
//
//        //siirrytään seuraavalle riville
//        rs.next();
//
//        //tehdään annos joka voidaan palauttaa
//        Annos annos = new Annos(rs.getInt("id"), rs.getString("nimi"));
//
//        stmt2.close();
//        rs.close();
//        conn.close();
//        
//        return annos;
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Annos WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

}
