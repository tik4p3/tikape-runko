
package tikape.runko.domain;

public class Raakaaine {
    
    private int id;
    private String nimi;

    public Raakaaine(Integer id, String nimi) {
        this.nimi = nimi;
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
