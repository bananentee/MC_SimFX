/**
 * @author Sven Ibe
 * @version
 */

package com.example.mc_sim_data;

public class Spieler {

    private final Welt welt;
    private Spitzhacke pickaxe;
    private Axt axe;

    private final int staerke;
    private int coins;
    private int holz;
    private int stein;
    private int eisen;

    public Spieler(int staerke, Welt welt) {
        this.staerke = staerke;
        this.welt = welt;
        this.coins = 0;
    }

    public void abbauen () {
        if (pickaxe != null) {
            int power = staerke + pickaxe.getWert();
            if (welt.getGenStein() <= 0) {
                System.out.println("[PLAYER] Stein gibt es nicht mehr!");
            } else {
                if (pickaxe.getMaterial().equals("Holz")) {
                    stein += power;
                    welt.subStein(power);
                } else { // if material is equal to stone or iron
                    stein += power;
                    welt.subStein(power);
                    if (welt.getGenEisen() <= 0) {
                        System.out.println("[PLAYER] Eisen gibt es nicht mehr!");
                    } else {
                        eisen += power;
                        welt.subEisen(power);
                    }
                }
            }
        }

        if (welt.getGenHolz() <= 0) {
            System.out.println("[PLAYER] Holz gibt es nicht mehr!");
        } else {
            if (axe != null) {
                holz += staerke + axe.getWert();
                welt.subHolz(staerke + axe.getWert());
            }
            holz += staerke;
            welt.subHolz(staerke);
        }

        showInv();
    }

    public void crafteSpitzhacke(String type) {
        switch (type) {
            case "Holz":
                if (holz >= 5) {
                    holz -= 5;
                    pickaxe = null;
                    pickaxe = new Spitzhacke(type);
                } else {
                    System.out.println("[PLAYER] Keine Spitzhacke kann erstellt werden");
                }
                break;
            case "Stein":
                if (holz >= 10 && stein >= 5) {
                    holz -= 10;
                    stein -= 5;
                    pickaxe = null;
                    pickaxe = new Spitzhacke(type);
                } else {
                    System.out.println("[PLAYER] Keine Spitzhacke kann erstellt werden");
                }
                break;
            case "Eisen":
                if (holz >= 20 && stein >= 10 && eisen >= 5) {
                    holz -= 20;
                    stein -= 10;
                    eisen -= 5;
                    pickaxe = null;
                    pickaxe = new Spitzhacke(type);
                } else {
                    System.out.println("[PLAYER] Keine Spitzhacke kann erstellt werden");
                }
                break;
            default:
                System.out.println("[PLAYER] Falscher Name wurde angegeben");
        }
    }

    public void crafteAxt (String type) {
        switch (type) {
            case "Holz":
                if (holz >= 5) {
                    holz -= 5;
                    axe = null;
                    axe = new Axt(type);
                } else {
                    System.out.println("[PLAYER] Keine Axt kann erstellt werden");
                }
                break;
            case "Stein":
                if (holz >= 10 && stein >= 5) {
                    holz -= 10;
                    stein -= 5;
                    axe = null;
                    axe = new Axt(type);
                } else {
                    System.out.println("[PLAYER] Keine Axt kann erstellt werden");
                }
                break;
            case "Eisen":
                if (holz >= 20 && stein >= 10 && eisen >= 5) {
                    holz -= 20;
                    stein -= 10;
                    eisen -= 5;
                    axe = null;
                    axe = new Axt(type);
                } else {
                    System.out.println("[PLAYER] Keine Axt kann erstellt werden");
                }
                break;
            default:
                System.out.println("[PLAYER] Falscher Name wurde angegeben");
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

    public boolean kaufe (int coins) {
        if (this.coins > coins) {
            this.coins -= coins;
            return true;
        } else {
            return false;
        }
    }

    public void showInv () {
        System.out.println("----------------------");
        System.out.println("Holz im Inventar: " + holz);
        System.out.println("Stein im Inventar: " + stein);
        System.out.println("Eisen im Inventar: " + eisen);
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

    public Spitzhacke getPickaxe() {
        return pickaxe;
    }

    public Axt getAxe() {
        return axe;
    }

    public void setCoins(int coins) { this.coins = coins; }
}