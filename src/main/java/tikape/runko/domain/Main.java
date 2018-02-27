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
import tikape.runko.database.AnnosDao;
import tikape.runko.database.RaakaaineAnnosDao;
import tikape.runko.database.RaakaaineDao;

public class Main {

    public static void main(String[] args) throws Exception {
        
        System.out.println("Hello World!");
        
        
        Database database = null;
        try {
            database = new Database();
        } catch (Exception e) {
            System.out.println("Virhe tietokantaan yhdistettäessä: " + e);
        }
        
        
        Connection conn = database.getConnection();
        ResultSet resultset = conn.prepareStatement("SELECT * FROM Annos").executeQuery();
        System.out.println("(Main) onko resultsetissä 'next()': " + resultset.next());
        System.out.println("(Main) Löydettyjen annosten nimet: ");
        while (resultset.next())   {
            System.out.println("(Main) " + resultset.getString("nimi"));
        }
        


        RaakaaineDao raakaaineetDao = new RaakaaineDao(database);
        RaakaaineAnnosDao raakaaineannoksetDao = new RaakaaineAnnosDao(database);
        AnnosDao annoksetDao = new AnnosDao(database);

        // Tämä ryhmitelty sivujen mukaan - etusivu ja neljä tai viisi muuta sivua
        // Jokaisella sivulla Sparkin get ja post -metodit, vaikka en olisi niille käyttöä keksinyt
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        //Etusivu
        Spark.get("/keittokirja", (req, res) -> {
            HashMap map = new HashMap<>();
            
            
            List<Annos> annokset = annoksetDao.findAll();
            
            map.put("annokset", annokset);
               for (Annos annos : annokset)   {
                System.out.println(" (Main) " + annos.getId() + "|" + annos.getNimi());
            }

            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "keittokirja"));
        });
    
        Spark.get("/annoslistaus/:id", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("annos", annoksetDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());
           

//        // RAAKA-AINEET
//        // Haetaan kaikki raaka-aineet
//        Spark.get("/haeaineet", (req, res) -> {
//
//            HashMap<String, Object> map = new HashMap();
//            map.put("raakaaineet", raakaaineetDao.findAll());
//
//            return new ModelAndView(map, "lisaaaine");
//        }, new ThymeleafTemplateEngine());
//
//        // Lisätään raaka-aine
//        Spark.post("/lisaaaine", (req, res) -> {
//
//            Raakaaine raakaaine = new Raakaaine(-1, req.queryParams("nimi"));
//            raakaaineetDao.saveOrUpdate(raakaaine);
//
//            res.redirect("/lisaaaine");
//            return "";
//        });
//
//        // Poistetaan yksittäinen raaka-aine
//        Spark.post("/poistaraakaaine", (req, res) -> {
//
//            Raakaaine raakaaine = new Raakaaine(-1, req.queryParams("nimi"));
//            raakaaineetDao.saveOrUpdate(raakaaine);
//
//            raakaaineetDao.delete(raakaaine.getId());
//
//            res.redirect("/poistaraakaaine");
//            return "";
//        });
//
//        
        // ANNOKSET
        // Haetaan kaikki annokset
        Spark.get("/annoslistaus", (req, res) -> {

            HashMap<String, Object> map = new HashMap();
            map.put("annokset", annoksetDao.findAll());

            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());

        // Lisätään annos
        Spark.post("/annoslistaus", (req, res) -> {

            Annos annos = new Annos(-1, req.queryParams("nimi"));
            annoksetDao.saveOrUpdate(annos);

            res.redirect("/annoslistaus");
            return "";
        });
//
//        // Poistetaan annos
//        Spark.post("/poistaannos", (req, res) -> {
//
//            Annos annos = new Annos(-1, req.queryParams("nimi"));
//            annoksetDao.saveOrUpdate(annos);
//
//            annoksetDao.delete(annos.getId());
//
//            res.redirect("/poistaannos");
//            return "";
//        });
//
//        
//        // RAAKAAINEANNOKSET
//        // Haetaan kaikki annokset
//        Spark.get("/annoslistaus", (req, res) -> {
//
//            HashMap<String, Object> map = new HashMap();
//            map.put("annokset", annoksetDao.findAll());
//
//            return new ModelAndView(map, "annokset");
//        }, new ThymeleafTemplateEngine());
//
//        // Lisätään annos
//        Spark.post("/annoslistaus", (req, res) -> {
//
//            Annos annos = new Annos(-1, req.queryParams("nimi"));
//            annoksetDao.saveOrUpdate(annos);
//
//            res.redirect("/annoslistaus");
//            return "";
//        });
//
//        // Poistetaan annos
//        Spark.post("/poistaannos", (req, res) -> {
//
//            Annos annos = new Annos(-1, req.queryParams("nimi"));
//            annoksetDao.saveOrUpdate(annos);
//
//            annoksetDao.delete(annos.getId());
//
//            res.redirect("/poistaannos");
//            return "";
//        });       
//        
//         Lisää annos “/lisaa-annos”
        Spark.get("/lisaa-annos", (req, res) -> {

            HashMap<String, Object> map = new HashMap();
            List<Raakaaine> raakaaineett = new ArrayList();
            List<RaakaaineAnnos> jolaitetut = new ArrayList();

            raakaaineett = raakaaineetDao.findAll();
            jolaitetut = raakaaineannoksetDao.findAll();


            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "lisaaannos"));
        });
//
//        Spark.post("/lisaa-annos", (req, res) -> {
//
//            // Haetaan Thymeleafilta tiedot Thymeleafin nimeämisten mukaan
//            String raakaaine = req.queryParams("raakaaine");
//            String maara = req.queryParams("maara");
//            String lisaohje = req.queryParams("lisaohje");
//
//            // haetaan nimen perusteella raaka-aine, jos tarvetta?                      *
//            // Raakaaine raakaaine2 = tarvittavametodi(raakaaine);
//            
//            Raakaaine raakaaine2 = 
//
//            // Javaan uusi raaka-aineohje, joka myöhemmin lisätään tietokantaan
//            RaakaaineAnnos raakaaineohje = new RaakaaineAnnos(raakaaine2, maara, lisaohje);
//            List<RaakaaineAnnos> raakaaineohjeet = new ArrayList();
//            raakaaineohjeet.add(raakaaineohje);
//
//            // Järjestyksen laskeminen ja laittaminen?
//            raakaaineohje.setJarjestys(raakaaineohjeet.size());
//
//            // Tietokantaan laittaminen
//            
//            
//            
//            
//            // Sivun päivittäminen
//            res.redirect("/lisaa-annos");
//
//            return "";
//        });
        Spark.get("/lisaa-aineita", (req, res) -> {

            HashMap<String, Object> map = new HashMap();


            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "lisaaaine"));
        });
          

        //TILASTOT
        //Tilastotietoa annoksista “/tilastotieto”
        Spark.get("/tilastotieto", (req, res) -> {
            HashMap<String, Object> map = new HashMap();
            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "tilastotieto"));
        });

        Spark.post("*", (req, res) -> {

            return "";
        });
    }
}
