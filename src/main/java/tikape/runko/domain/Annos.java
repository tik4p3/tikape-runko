
package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

public class Annos {
    
    private Integer id;
    private String nimi;
    private List<Raakaaine> aineet;
 

    public Annos(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
        this.aineet = new ArrayList<>();
    }

    public int getId(){
        return id;
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

    public List<Raakaaine> getAineet() {
        return aineet;
    }
}
