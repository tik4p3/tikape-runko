/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tikaperyhmatyo2_internetsovellus;

/**
 *
 * @author Joona Niemelä
 */
public class Raakaaineohje {
    
    private Raakaaine raakaaine;
    private String maara;
    private String lisaohje;
    private int jarjestys;

    public void setJarjestys(int jarjestys) {
        this.jarjestys = jarjestys;
    }

    public Raakaaineohje(Raakaaine raakaaine, String maara, String lisaohje) {
        this.raakaaine = raakaaine;
        this.maara = maara;
        this.lisaohje = lisaohje;
    }

    public Raakaaineohje(Raakaaine raakaaine, String maara) {
        this.raakaaine = raakaaine;
        this.maara = maara;
    }

    public Raakaaine getRaakaaine() {
        return raakaaine;
    }

    public String getMaara() {
        return maara;
    }

    public String getLisaohje() {
        return lisaohje;
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
