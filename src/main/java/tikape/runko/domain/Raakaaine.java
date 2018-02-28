
package tikape.runko.domain;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tikape.runko.database.RaakaaineDao;

public class Raakaaine {
    
    private int id;
    private String nimi;

    public Raakaaine(int id, String nimi) {
        this.nimi = nimi;
        this.id = id;
        
        System.out.println("(Raakaaine, konstruktori 1) Lisättiin raaka-aine: " + this.id + "|" + this.nimi);
    }
    public Raakaaine(String nimi) {
        this.nimi = nimi;
        
        System.out.println("(Raakaaine, konstruktori 2) Lisättiin ilman id-numeroa raaka-aine: " + this.nimi);
    }
    
    public int getId(){
        return id;
    }

    public String getNimi() {
        return nimi;
    }
    
    public void setNimi(String nimi){
        this.nimi = nimi;
    }
    
}
