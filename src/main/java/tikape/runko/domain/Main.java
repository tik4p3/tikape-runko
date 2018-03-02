package tikape.runko.domain;

import java.sql.Connection;
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
    
    static String valittuannos;
    static public int i = 100;

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
        while (resultset.next()) {
            System.out.println("(Main) " + resultset.getString("nimi"));
        }

        RaakaaineDao raakaaineDao = new RaakaaineDao(database);
        RaakaaineAnnosDao raakaaineAnnosDao = new RaakaaineAnnosDao(database);
        AnnosDao annosDao = new AnnosDao(database);

        // Lisää annoksen lista, jossa on valitut raaka-aineet, mutta vielä niin, että niitä ei lisätä tietokantaan
        // Näin ei tarvitse esimerkiksi annosId-tunnusta olla, kun sitä ei vielä edes ole
        List<Jolaitettu> jolaitettuja = new ArrayList();
        
        // Annos, johon lisätään raaka-aineita
        
       

        // Tämä ryhmitelty sivujen mukaan - etusivu ja neljä tai viisi muuta sivua
        // Jokaisella sivulla Sparkin get ja post -metodit, vaikka en olisi niille käyttöä keksinyt
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        //Ylimääräinen etusivu
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();

            List<Annos> annokset = annosDao.findAll();

            map.put("annokset", annokset);
            for (Annos annos : annokset) {
                System.out.println(" (Main) " + annos.getId() + "|" + annos.getNimi());
            }

            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "keittokirja"));
        });

        //Etusivu
        Spark.get("/keittokirja", (req, res) -> {
            HashMap map = new HashMap<>();

            List<Annos> annokset = annosDao.findAll();

            map.put("annokset", annokset);
            for (Annos annos : annokset) {
                System.out.println(" (Main) " + annos.getId() + "|" + annos.getNimi());
            }

            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "keittokirja"));
        });

        Spark.get("/annoslistaus/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            
            int annosId = Integer.parseInt(req.params("id"));
            
            List<RaakaaineAnnos> kaikki = raakaaineAnnosDao.findAll();
            List<RaakaaineAnnos> oikeat = new ArrayList();
            
            for (RaakaaineAnnos a: kaikki)   {
                if (a.getAnnosId() == annosId)   {
                    a.setNimi(annosDao.findOne(annosId).getNimi());
                    oikeat.add(a);
                }
            }
            
            

            map.put("annos", annosDao.findOne(annosId));
            map.put("aineet", oikeat);
            
            

            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());

//        // RAAKA-AINEET
//        // Haetaan kaikki raaka-aineet
        Spark.get("/haeaineet", (req, res) -> {

            HashMap<String, Object> map = new HashMap();
            map.put("raakaaineet", raakaaineDao.findAll());

            return new ModelAndView(map, "lisaaaine");
        }, new ThymeleafTemplateEngine());

        // Lisätään raaka-aine
        Spark.post("/lisaa-aineita", (req, res) -> {

            Raakaaine raakaaine = new Raakaaine(-1, req.queryParams("raakaaine"));
            raakaaineDao.saveOrUpdate(raakaaine);

            res.redirect("/lisaa-aineita");
            return "";
        });

        // ANNOKSET
        // Haetaan kaikki annokset
        Spark.get("/annoslistaus", (req, res) -> {

            HashMap<String, Object> map = new HashMap();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());

        // Lisätään annos
        Spark.post("/annoslistaus", (req, res) -> {

            Annos annos = new Annos(-1, req.queryParams("nimi"));
            annosDao.saveOrUpdate(annos);

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
            raakaaineett = raakaaineDao.findAll();
            
            List<Annos> annoksetUus = new ArrayList();
            annoksetUus = annosDao.findAll();
           
            map.put("annokset", annoksetUus);
            map.put("raakaaineet", raakaaineett);
            map.put("jolaitetut", jolaitettuja);

            System.out.println("(Main, get lisaa-annos) mappiin lisätty raakaaineet ja jolaitetut");

            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "lisaaannos"));
        });
        
        Spark.post("/lisaa-annos/lisaa_raaka-aine", (req, res) -> {

            // Haetaan Thymeleafilta tiedot Thymeleafin nimeämisten mukaan
            String raakaaine = req.queryParams("raakaaine");
            String lukumaara = req.queryParams("lukumaara");
            String lisaohje = req.queryParams("lisaohje");

            System.out.println("(Main, lisaa-annos) Saatiin seuraavat: "
                    + raakaaine + " (raaka-aine), " + lukumaara
                    + " (määrä), ja " + lisaohje + " (lisäohje)");

            int id = raakaaineDao.findId(raakaaine);
            System.out.println("(Main, lisaa-annos) Id on: " + id);
            Raakaaine aine = raakaaineDao.findOne(id);
            
            
            
            int annosId = annosDao.findId(valittuannos);
            
            // järjestyksen osoittava int
            int i = 1;
            
            
            RaakaaineAnnos raakaaineannos = new RaakaaineAnnos(id, annosId);
            raakaaineannos.setLisaohje(lisaohje);
            raakaaineannos.setMaara(lukumaara);
            raakaaineannos.setJarjestys(i);

            raakaaineAnnosDao.saveOrUpdate(raakaaineannos);

            // Sivun päivittäminen
            res.redirect("/lisaa-annos");

            return "";
        });

//        Spark.post("/lisaa-annos", (req, res) -> {
//
//            // Haetaan Thymeleafilta tiedot Thymeleafin nimeämisten mukaan
//            String raakaaine = req.queryParams("raakaaine");
//            String lukumaara = req.queryParams("lukumaara");
//            String lisaohje = req.queryParams("lisaohje");
//
//            System.out.println("(Main, lisaa-annos) Saatiin seuraavat: "
//                    + raakaaine + " (raaka-aine), " + lukumaara
//                    + " (määrä), ja " + lisaohje + " (lisäohje)");
//
//            int id = raakaaineDao.findId(raakaaine);
//            System.out.println("(Main, lisaa-annos) Id on: " + id);
//            Raakaaine aine = raakaaineDao.findOne(id);
//
//            jolaitettuja.add(new Jolaitettu(aine, aine.getNimi(), lisaohje, lukumaara));
//
//            // Sivun päivittäminen
//            res.redirect("/lisaa-annos");
//
//            return "";
//        });

//        Spark.post("/lisaa-annos/lisataan", (req, res) -> {
//
//            String annosNimi = req.queryParams("nimi");
//
//            System.out.println("(Main /lisaa-annos/lisataan, post) Lisätään annosta: " + annosNimi);
//
//            annosDao.saveOrUpdate(annosNimi);
//            
//            int annosId = annosDao.findId(annosNimi);
//            
//            for (Jolaitettu jolaitettu : jolaitettuja)    {
//                int i = 1;
//                String raakaaineNimi = jolaitettu.getRaakaaine().getNimi();
//                int raakaaineId = raakaaineDao.findId(raakaaineNimi);
//                RaakaaineAnnos raakaaineannos = new RaakaaineAnnos(raakaaineId, annosId);
//                raakaaineannos.setMaara(jolaitettu.getMaara());
//                raakaaineannos.setLisaohje(jolaitettu.getLisaohje());
//                raakaaineannos.setJarjestys(i);
//                raakaaineAnnosDao.saveOrUpdate(raakaaineannos);
//                i++;
//            }
//            
//            jolaitettuja.clear();
//            
//            // Sivun päivittäminen
//            res.redirect("/lisaa-annos");
//
//            return "";
//        });

Spark.post("/lisaa-annos/lisataan", (req, res) -> {

            String annosNimi = req.queryParams("nimi");

            System.out.println("(Main /lisaa-annos/lisataan, post) Lisätään annosta: " + annosNimi);

            annosDao.saveOrUpdate(annosNimi);
            
            int annosId = annosDao.findId(annosNimi);
            
            // Sivun päivittäminen
            res.redirect("/lisaa-annos");

            return "";
        });

        Spark.post("/lisaa-annos/valitse_annos", (req, res) -> {

            valittuannos = req.queryParams("nimi");
            // Sivun päivittäminen
            res.redirect("/lisaa-annos");

            return "";
        });

        Spark.get("/lisaa-aineita", (req, res) -> {

            HashMap<String, Object> map = new HashMap();
            map.put("raakaaineet", raakaaineDao.findAll());

            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "lisaaaine"));
        });

        Spark.post("/lisaa-aineita", (req, res) -> {

            // poistotoiminnallisuus ei vielä toimi
            if (!req.queryParams("poistettava").isEmpty()) {
                // poistettavan raaka-aineen id saadaan suoraan selville
                int poistettavanId = Integer.parseInt(req.queryParams("poistettava"));

                // raaka-aine on poistettava ensin annoksista, joissa se on käytössä
                raakaaineAnnosDao.poistaRaakaaineenMukaan(poistettavanId);

                //poistetaan lopuksi raaka-aine tietokannasta
                raakaaineDao.delete(poistettavanId);

                /*Alkuperäinen versio:
                Raakaaine raakaaine = new Raakaaine(req.queryParams("poistettava"));
                raakaaineDao.saveOrUpdate(raakaaine);
                
                raakaaineDao.delete(raakaaine.getId());*/
                res.redirect("/lisaa-aineita");
            }

            System.out.println("Lisätään ainetta: " + req.queryParams("raakaaine"));

            raakaaineDao.saveOrUpdate(req.queryParams("raakaaine"));

            res.redirect("/lisaa-aineita");

            return "";
        });

        // Poistetaan yksittäinen raaka-aine (ei käytössä)
        Spark.post("/poistaraakaaine", (req, res) -> {

            Raakaaine raakaaine = new Raakaaine(req.queryParams("poistettava"));
            raakaaineDao.saveOrUpdate(raakaaine);

            raakaaineDao.delete(raakaaine.getId());

            res.redirect("/lisaa-aineita");
            return "";
        });

        //TILASTOT
        //Tilastotietoa annoksista “/tilastotieto”
        Spark.get("/tilastotieto", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Annos> annokset = new ArrayList<>();
            annokset = annosDao.findAll();
            map.put("annokset",annokset);
            map.put("raakaaineet", raakaaineDao.findAll());
            
            return new ThymeleafTemplateEngine().render(new ModelAndView(map, "tilastotieto"));
        });

        Spark.post("/tilasto", (req, res) -> {
            String aineNimi = req.queryParams("nimi");
            int aineId = raakaaineDao.findId(aineNimi);
            

            return "";
        });
    }
}
