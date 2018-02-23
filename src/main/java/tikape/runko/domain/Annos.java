
package com.mycompany.tikaperyhmatyo2_internetsovellus;

import java.util.ArrayList;
import java.util.List;

public class Ohje {
    
    private String nimi;
    private List<Raakaaineohje> aineet;
 

    public Ohje(String nimi) {
        this.nimi = nimi;
        this.aineet = new ArrayList<>();
    }


    public void setAine(Raakaaineohje raakaaineohje) {
        this.aineet.add(raakaaineohje);
    }

    public String getNimi() {
        return nimi;
    }

    public List<Raakaaineohje> getAineet() {
        return aineet;
    }
}