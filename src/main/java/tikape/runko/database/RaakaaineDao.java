package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Database;
import tikape.runko.domain.Raakaaine;

public class RaakaaineDao implements Dao<Raakaaine, Integer> {

    private Database database;

    public RaakaaineDao(Database database) {
        this.database = database;
    }

    @Override
    public Raakaaine findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Raakaaine WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Raakaaine raakaaine = new Raakaaine(rs.getInt("id"), rs.getString("nimi"));

        System.out.println("löytyi raakaaine: " + rs.getInt("id") + "|" + rs.getString("nimi"));

        stmt.close();
        rs.close();
        conn.close();

        return raakaaine;
    }

    @Override
    public List<Raakaaine> findAll() throws SQLException {

        List<Raakaaine> raakaaineet = new ArrayList<>();

        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Raakaaine");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Raakaaine raakaaine = new Raakaaine(rs.getInt("id"), rs.getString("nimi"));

            raakaaineet.add(raakaaine);

        }

        stmt.close();
        rs.close();
        conn.close();

        return raakaaineet;

    }

    @Override
    public Raakaaine saveOrUpdate(Raakaaine object) throws SQLException {

        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Raakaaine (nimi) VALUES (?)");
        /*stmt.setInt(1, object.getId());*/
        stmt.setString(1, object.getNimi());
        stmt.executeUpdate();
        stmt.close();

        PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM Raakaaine WHERE nimi = ?");
        stmt2.setString(1, object.getNimi());
        ResultSet rs = stmt2.executeQuery();

        //siirrytään seuraavalle riville
        rs.next();

        //tehdään raaka-aine joka voidaan palauttaa
        Raakaaine raakaaine = new Raakaaine(rs.getInt("id"), rs.getString("nimi"));

        stmt2.close();
        rs.close();
        conn.close();
        
        return raakaaine;
    }

    public Raakaaine saveOrUpdate(String nimi) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Raakaaine (nimi) VALUES (?)");
            stmt.setString(1, nimi);
            stmt.executeUpdate();
        }

        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Raakaaine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public int findId(String aine) throws SQLException {
        int id = -1;

        System.out.println("(RaakaaineDao) Etsitään ainetta: " + aine);

        List<Raakaaine> raakaaineet;

        try (Connection connection = database.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Raakaaine");
            ResultSet rs = stmt.executeQuery();
            raakaaineet = new ArrayList<>();
            while (rs.next()) {

                int loydettyId = rs.getInt("id");
                String loydettyNimi = rs.getString("nimi");

                System.out.println("(RaakaaineDao, findId) Löytyi raaka-aine: " + loydettyId + "|" + loydettyNimi);
                Raakaaine raakaaine = new Raakaaine(loydettyId, loydettyNimi);

                raakaaineet.add(raakaaine);

            }
            stmt.close();
            rs.close();

            for (Raakaaine raakaaine : raakaaineet) {
                if (raakaaine.getNimi().equals(aine)) {
                    id = raakaaine.getId();

                    System.out.println("(RaakaaineDao, findId) Löydettiin oikea avain: " + id);
                }
            }

        }

        return id;
    }

}
