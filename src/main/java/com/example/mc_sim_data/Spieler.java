/**
 * @author Sven Ibe
 * @version
 */

package com.example.mc_sim_data;

public class Spieler {

    private String name;
    private Welt welt;
    private Spitzhacke temp;

    private int staerke;
    private int coins;
    private int holz;
    private int stein;
    private int eisen;


    public Spieler(String name, int staerke, Welt welt) {
        this.name = name;
        this.staerke = staerke;
        this.welt = welt;
        this.coins = 0;
    }


    public void abbauen () {
        if (temp != null) {
            if (temp.getMaterial().equals("Holz")) {
                welt.subStein(staerke + temp.getWert());
                stein += staerke + temp.getWert();
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
        //TODO implement method
    }

    public void verkaufe (String type, int quantity) {
        switch (type) {
            case "Holz":
                holz -= quantity;
                coins += 5 * quantity;
            case "Stein":
                stein -= quantity;
                coins += 10 * quantity;
            case "Eisen":
                stein -= quantity;
                coins += 15 * quantity;
            case "Alles":
                coins += (5 * holz) + (10 * stein) + (5 * eisen);
                holz = 0;
                stein = 0;
                eisen = 0;
        }
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

    public int getHolz() {
        return holz;
    }

    public int getCoins() {
        return coins;
    }

    public int getStein() {
        return stein;
    }

    public int getEisen() {
        return eisen;
    }
}