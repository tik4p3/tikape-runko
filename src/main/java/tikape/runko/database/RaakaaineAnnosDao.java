package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Database;
import tikape.runko.domain.RaakaaineAnnos;

public class RaakaaineAnnosDao implements Dao<RaakaaineAnnos, Integer> {

    private Database database;

    public RaakaaineAnnosDao(Database database) {
        this.database = database;
    }

    @Override
    public RaakaaineAnnos findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaaineAnnos WHERE raakaaine_id = ?, ");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        RaakaaineAnnos raakaaine = new RaakaaineAnnos(rs.getInt("id"), rs.getInt("id"));

        stmt.close();
        rs.close();
        conn.close();

        return raakaaine;
    }

    @Override
    public List<RaakaaineAnnos> findAll() throws SQLException {

        List<RaakaaineAnnos> raakaaineet;

        try (Connection connection = database.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaaineAnnos");
            ResultSet rs = stmt.executeQuery();

            raakaaineet = new ArrayList<>();

            while (rs.next()) {

                RaakaaineAnnos raakaaine = new RaakaaineAnnos(rs.getInt("raaka_aine_id"), rs.getInt("annos_id"));

                //raakaaineet.add(raakaaine); rs.getString("maara"), rs. rs.getString("nimi");

            }
            stmt.close();
            rs.close();
        }

        return raakaaineet;

    }

    @Override
    public RaakaaineAnnos saveOrUpdate(RaakaaineAnnos object) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaaineAnnos (raaka_aine_id, annos_id, maara, jarjestys, ohje) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, object.getRaakaaineId());
            stmt.setInt(2, object.getAnnosId());
            stmt.setString(3, object.getMaara());
            stmt.setInt(4, object.getJarjestys());
            stmt.setString(5, object.getLisaohje());
            stmt.executeUpdate();
        }

        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Raakaaine WHERE raaka_aine_id = ?, annos_id = ?");

        stmt.setInt(1, key);
        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    // en tiedä onko tästä apua 
    public void poistaRaakaaineenMukaan(int poistettavanId) throws SQLException {
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaaineAnnos WHERE raakaaine_id = ?");

        stmt.setInt(1, poistettavanId);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

   

}
