
package tikape.runko.domain;

/**
 *
 * @author SDG
 */
public class Jolaitettu {
    
    private String nimi;
    private String lisaohje;
    private String maara;
    private Raakaaine raakaaine;

    public Jolaitettu (Raakaaine raakaaine, String nimi, String lisaohje, String maara) {
        this.raakaaine = raakaaine;
        this.nimi = nimi;
        this.lisaohje = lisaohje;
        this.maara = maara;
        
        System.out.println("Uusi jolaitettu: " + this.nimi + ", " + this.maara + ", " + this.lisaohje);
    }

    public String getNimi() {
        return nimi;
    }

    public String getLisaohje() {
        return lisaohje;
    }

    public String getMaara() {
        return maara;
    }
    
    public Raakaaine getRaakaaine() {
        return raakaaine;
    }
    
    
    
    
}
