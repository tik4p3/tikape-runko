package tikape.runko.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        stmt.close();
        rs.close();
        conn.close();

        return raakaaine;
    }

    @Override
    public List<Raakaaine> findAll() throws SQLException {

        List<Raakaaine> raakaaineet;

        try (Connection connection = database.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Raakaaine");
            ResultSet rs = stmt.executeQuery();
            raakaaineet = new ArrayList<>();
            while (rs.next()) {

                Raakaaine raakaaine = new Raakaaine(rs.getInt("id"), rs.getString("nimi"));

                raakaaineet.add(raakaaine);

            }
            stmt.close();
            rs.close();
        }

        return raakaaineet;

    }

    @Override
    public Raakaaine saveOrUpdate(Raakaaine object) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Raakaaine (id, nimi) VALUES (?, ?)");
            stmt.setInt(1, object.getId());
            stmt.setString(2, object.getNimi());
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

}
