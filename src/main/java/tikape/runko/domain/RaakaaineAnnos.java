
package tikape.runko.domain;

public class RaakaaineAnnos {

    private Raakaaine raakaaine;
    private Annos annos;
    private String maara;
    private String lisaohje;
    private int jarjestys;
    
    // tarvitaankohan näitä
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
    
    // uusi konstruktori
    public RaakaaineAnnos(Annos annos, Raakaaine raakaaine, int jarjestys, String maara, String lisaohje) {
        this.annos = annos;
        this.raakaaine = raakaaine;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.lisaohje = lisaohje;
    }
    

    public Raakaaine getRaakaaine() {
        return raakaaine;
    }

    public Annos getAnnos() {
        return annos;
    }
    
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

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public void setLisaohje(String lisaohje) {
        this.lisaohje = lisaohje;
    }

}
