package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Annos;
import tikape.runko.domain.Database;
import static tikape.runko.domain.Main.i;
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

    public Annos saveOrUpdate(String annos) throws SQLException {

        try (Connection conn = database.getConnection()) {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos (id, nimi) VALUES (?, ?)");
        stmt.setInt(1, i);
        i++;
        stmt.setString(2, annos);
            stmt.executeUpdate();
        }
        
        

        return null;
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
    
    public int findId (String aine) throws SQLException   {
        int id = -1;
        
        System.out.println("(RaakaaineDao) Etsitään ainetta: " + aine);
        
        List<Annos> raakaaineet;

        try (Connection connection = database.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Annos");
            ResultSet rs = stmt.executeQuery();
            raakaaineet = new ArrayList<>();
            while (rs.next()) {
                
                int loydettyId = rs.getInt("id");
                String loydettyNimi = rs.getString("nimi");
                
                System.out.println("(RaakaaineDao, findId) Löytyi raaka-aine: " + loydettyId + "|" + loydettyNimi);
                Annos annos = new Annos(loydettyId, loydettyNimi);

                raakaaineet.add(annos);

            }
            stmt.close();
            rs.close();
            
            
            for (Annos annos : raakaaineet) {
                if (annos.getNimi().equals(aine))    {
                    id = annos.getId();
                    
                    System.out.println("(RaakaaineDao, findId) Löydettiin oikea avain: " + id);
                }
            }
            
        }
        
        
        return id;
    }

}
