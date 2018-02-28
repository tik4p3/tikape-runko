
package tikape.runko.domain;

public class RaakaaineAnnos {

    private Raakaaine raakaaine;
    private String maara;
    private String lisaohje;
    private int jarjestys;

    private int raakaaine_id;
    private int annos_id;


    public RaakaaineAnnos(Integer raakaaine_id, Integer annos_id) {
        this.raakaaine_id = raakaaine_id;
        this.annos_id = annos_id;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.lisaohje = lisaohje;
    }
    
    public RaakaaineAnnos(Integer raakaaine_id, String maara, String lisaohje) {
        this.raakaaine_id = raakaaine_id;
        this.maara = maara;
        this.lisaohje = lisaohje;
    }
    
       
       
    

//    public RaakaaineAnnos(Raakaaine raakaaine, String maara) {
//        this.raakaaine = raakaaine;
//        this.maara = maara;
//    }
//    public Raakaaine getRaakaaine() {
//        return raakaaine;
//    }

    public int getRaakaaineId(){
        return raakaaine_id;      
    }
    
    public int getAnnosId(){
        return annos_id;
    }
    
    public String getMaara() {
        return maara;
    }
    
    public int getJarjestys(){
        return jarjestys;
    }

    public String getLisaohje() {
        return lisaohje;
    }

    public void setJarjestys(int jarjestys) {
        this.jarjestys = jarjestys;
    }

    public void setRaakaaine(Raakaaine raakaaine) {
        this.raakaaine = raakaaine;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public void setLisaohje(String lisaohje) {
        this.lisaohje = lisaohje;
    }

}
