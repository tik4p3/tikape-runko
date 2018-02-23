
package com.mycompany.tikaperyhmatyo2_internetsovellus;

import java.util.ArrayList;
import java.util.List;

public class Ohje {
    
    private String nimi;
    private List<RaakaaineAnnos> aineet;
 

    public Ohje(String nimi) {
        this.nimi = nimi;
        this.aineet = new ArrayList<>();
    }


    public void setAine(RaakaaineAnnos raakaaineohje) {
        this.aineet.add(raakaaineohje);
    }

    public String getNimi() {
        return nimi;
    }

    public List<RaakaaineAnnos> getAineet() {
        return aineet;
    }
}
