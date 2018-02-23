
package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

public class Annos {
    
    private String nimi;
    private List<Raakaaine> aineet;
 

    public Annos(String nimi) {
        this.nimi = nimi;
        this.aineet = new ArrayList<>();
    }


    public void setNimi(){
        this.nimi = nimi;
    }
    
    public void setAine(Raakaaine raakaaine) {
        this.aineet.add(raakaaine);
    }

    public String getNimi() {
        return nimi;
    }

    public List<Raakaaineohje> getAineet() {
        return aineet;
    }
}
