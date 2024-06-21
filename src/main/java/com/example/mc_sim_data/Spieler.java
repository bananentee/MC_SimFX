/**
 * @author Sven Ibe
 * @version
 */

package com.example.mc_sim_data;

public class Spieler {

    private String name;
    private Welt welt;
    private Spitzhacke pickaxe;
    private Axt axe;

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
        if (pickaxe != null) {
            if (pickaxe.getMaterial().equals("Holz")) {
                welt.subStein(staerke + pickaxe.getWert());
                stein += staerke + pickaxe.getWert();
            } else {
                welt.subStein(staerke + pickaxe.getWert());
                stein += staerke + pickaxe.getWert();
                welt.subEisen(staerke + pickaxe.getWert());
                eisen += staerke + pickaxe.getWert();
            }
        }

        if (axe != null) {
            welt.subHolz(staerke + axe.getWert());
            holz += staerke + axe.getWert();
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
                    pickaxe = null;
                    pickaxe = new Spitzhacke(type);
                }
                break;
            case "Stein":
                if (holz <= 10 && stein <= 5) {
                    System.out.println("keine Spitzhacke kann erstellt werden");
                } else {
                    holz -= 10;
                    stein -= 5;
                    pickaxe = null;
                    pickaxe = new Spitzhacke(type);
                }
                break;
            case "Eisen":
                if (holz <= 20 && stein <= 10 && eisen <= 5) {
                    System.out.println("keine Spitzhacke kann erstellt werden");
                } else {
                    holz -= 20;
                    stein -= 10;
                    eisen -= 5;
                    pickaxe = null;
                    pickaxe = new Spitzhacke(type);
                }
                break;
            default:
                System.out.println("falscher Name wurde angegeben");
        }
    }
    public void crafteAxt (String type) {
        switch (type) {
            case "Holz":
                if (holz <= 5) {
                    System.out.println("keine Axt kann erstellt werden");
                } else {
                    holz -= 5;
                    axe = null;
                    axe = new Axt(type);
                }
                break;
            case "Stein":
                if (holz <= 10 && stein <= 5) {
                    System.out.println("keine Axt kann erstellt werden");
                } else {
                    holz -= 10;
                    stein -= 5;
                    axe = null;
                    axe = new Axt(type);
                }
                break;
            case "Eisen":
                if (holz <= 20 && stein <= 10 && eisen <= 5) {
                    System.out.println("keine Axt kann erstellt werden");
                } else {
                    holz -= 20;
                    stein -= 10;
                    eisen -= 5;
                    axe = null;
                    axe = new Axt(type);
                }
                break;
            default:
                System.out.println("falscher Name wurde angegeben");
        }
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
        System.out.println("----------------------");
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