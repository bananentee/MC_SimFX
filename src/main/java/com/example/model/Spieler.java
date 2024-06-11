/**
 * @author Sven Ibe
 * @version
 */

package com.example.model;

public class Spieler {

    private String name;
    private Welt welt;
    private int staerke;
    private int coins;
    private int holz;
    private int stein;
    private int eisen;
    private Spitzhacke temp;

    public Spieler(String name, int staerke, Welt welt) {
        this.name = name;
        this.staerke = staerke;
        this.welt = welt;
    }


    public void abbauen () {
        if (temp != null) {
            if (temp.getMaterial().equals("Holz")) {
                welt.subStein(staerke + temp.getWert());
                stein = stein + staerke + temp.getWert();
            }
        }
        welt.subHolz(staerke);
        holz += staerke;

        showInv();
    }

    public void crafteSpitzhacke(String type) {
        switch (type) {
            case "Holz":
                if (holz <= 5) {
                    System.out.println("keine Spitzhacke kann erstellt werden");
                } else {
                    holz -= 5;
                    temp = null;
                    temp = new Spitzhacke(type);
                }
                break;
            case "Stein":
                if (holz <= 10 && stein <= 5) {
                    System.out.println("keine Spitzhacke kann erstellt werden");
                } else {
                    holz -= 10;
                    stein -= 5;
                    temp = null;
                    temp = new Spitzhacke(type);
                }
                break;
            case "Eisen":
                if (holz <= 20 && stein <= 10 && eisen <= 5) {
                    System.out.println("keine Spitzhacke kann erstellt werden");
                } else {
                    holz -= 20;
                    stein -= 10;
                    eisen -= 5;
                    temp = null;
                    temp = new Spitzhacke(type);
                }
                break;
            default:
                System.out.println("falscher Name wurde angegeben");
        }
    }
    public void crafteAxt () {

    }

    public void verkaufe () {

    }
    public void showInv () {
        System.out.println("Holz im Inventar: " + holz);
        System.out.println("Stein im Inventar: " + stein);
        System.out.println("Eisen im Inventar: " + eisen);
    }

    public void resetInv () {
        holz = 0;
        stein = 0;
        eisen = 0;
    }
}
