/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Joona Niemelä
 */

public class Raakaaine {
    
    private String nimi;

    public Raakaaine(String nimi) {
        this.nimi = nimi;
    }

    public String getNimi() {
        return nimi;
    }
    
    public void setNimi(){
        this.nimi = nimi;
    }
    
}
