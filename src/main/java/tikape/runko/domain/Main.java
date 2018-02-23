package tikape.runko.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) throws Exception {

        Database database = new Database("jdbc:sqlite:database.db"); // Muutetaan tietokannan nimi oikeaksi

        RaakaaineDao raakaaineet = new RaakaaineDao(database);
        RaakaaineAnnosDao raakaaineannokset = new RaakaaineAnnosDao(database);
        AnnosDao annokset = new AnnosDao(database);


        // Tämä ryhmitelty sivujen mukaan - etusivu ja neljä tai viisi muuta sivua
        // Jokaisella sivulla Sparkin get ja post -metodit, vaikka en olisi niille käyttöä keksinyt
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

  
        //Etusivu
        Spark.get("/keittokirja", (req, res) -> {

            // tietokanta, jossa on varsinaiset annos
            // osoite pitää vielä lisätä
            Database databaseOhjeet = new Database("ei vielä ole");

            // tietokannasta hakea annos nimet
            List<String> annokset = new ArrayList<>();

            // avaa yhteys tietokantaan
            // tietokannalle pitää vielä lisätä osoite                     *
            Connection conn = databaseOhjeet.getConnection();
            // tee kysely
            PreparedStatement stmt = conn.prepareStatement("SELECT nimi FROM Ohjeet");

            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                String nimi = tulos.getString("nimi");
                annokset.add(nimi);
            }

            HashMap<String, Object> map = new HashMap();

            map.put("annos", annokset);

            conn.close();

            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "keittokirja"));
        });

        Spark.post("*", (req, res) -> {

            return "";
        });

        
        // RAAKA-AINEET
        Spark.get("/lisaaaine", (req, res) -> {

            // haetaan tietokannasta olemassaolevat raaka-aineet, ja lisätään ne listaan      
            List<Raakaaine> raakaaineet = new ArrayList();

            // Lähetetään ne Thymeleafin avulla HTML-juttuihin            
            HashMap<String, Object> map = new HashMap();
            map.put("raakaaineet", raakaaineet);
            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "lisaaaine"));
        });

        Spark.post("/lisaaaine", (req, res) -> {

            Raakaaine raakaaine = new Raakaaine(req.queryParams("nimi"));
            raakaaineet.saveOrUpdate(raakaaine);
            
            res.redirect("/annos");
            return "";
        });

        
        
        // ANNOKSET
        //Lista annoksista “/annoslistaus/1” (jokaisen annoksen nimessä linkki)
        //tekemättä
        Spark.get("/annoslistaus", (req, res) -> {
            HashMap<String, Object> map = new HashMap();
            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "Path to template"));
        });

        Spark.post("*", (req, res) -> {

            return "";
        });

        
        // Lisää annos “/lisaa-annos”
        Spark.get("/lisaa-annos", (req, res) -> {

            HashMap<String, Object> map = new HashMap();
            List<String> raakaaineet = new ArrayList();

            // yhteys raaka-ainetietokantaan, osoite puuttuu
            Database databaseRaakaaine = new Database("osoite");
            Connection con = databaseRaakaaine.getConnection();

            // Etsitään raaka-aineiden nimet
            PreparedStatement stmt = con.prepareStatement("SELECT nimi FROM Raaka-aineet");

            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                String nimi = tulos.getString("nimi");
                raakaaineet.add(nimi);
            }

            // Lista jo laitetuista raaka-aineista                                      *
            // raakaaineohje-tietokannasta pitää etsiä tämän annoksen raaka-aineet      *
            List<RaakaaineAnnos> joLaitetut = new ArrayList();

            map.put("raakaaineet", raakaaineet);
            map.put("laitetut", joLaitetut);

            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "Path to template"));
        });

        Spark.post("/lisaa-annos", (req, res) -> {

            // Haetaan Thymeleafilta tiedot Thymeleafin nimeämisten mukaan
            String raakaaine = req.queryParams("raakaaine");
            String maara = req.queryParams("maara");
            String lisaohje = req.queryParamOrDefault("lisaohje", "");

            // haetaan nimen perusteella raaka-aine, jos tarvetta?                      *
            Raakaaine raakaaine2 = tarvittavametodi(raakaaine);

            // Javaan uusi raaka-aineohje, joka myöhemmin lisätään tietokantaan
            RaakaaineAnnos raakaaineohje = new RaakaaineAnnos(raakaaine2, maara, lisaohje);
            List<RaakaaineAnnos> raakaaineohjeet = new ArrayList();
            raakaaineohjeet.add(raakaaineohje);

            // Järjestyksen laskeminen ja laittaminen?
            raakaaineohje.setJarjestys(raakaaineohjeet.size());

            // Tietokantaan laittaminen                                                 *
            // Sivun päivittäminen
            res.redirect("/lisaa-annos");

            return "";
        });

        
        //TILASTOT
        //Tilastotietoa annoksista “/tilastoja”
        Spark.get("/tilastoja", (req, res) -> {
            HashMap<String, Object> map = new HashMap();
            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "Path to template"));
        });

        Spark.post("*", (req, res) -> {

            return "";
        });
    }
}
