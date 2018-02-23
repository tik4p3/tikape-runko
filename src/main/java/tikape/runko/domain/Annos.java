
package com.mycompany.tikaperyhmatyo2_internetsovellus;

import java.util.ArrayList;
import java.util.List;

public class Annos {
    
    private String nimi;
    private List<RaakaaineAnnos> aineet;
 

    public Annos(String nimi) {
        this.nimi = nimi;
        this.aineet = new ArrayList<>();
    }


    public void setAine(RaakaaineAnnos raakaaineannos) {
        this.aineet.add(raakaaineannos);
    }

    public String getNimi() {
        return nimi;
    }

    public List<RaakaaineAnnos> getAineet() {
        return aineet;
    }
}
