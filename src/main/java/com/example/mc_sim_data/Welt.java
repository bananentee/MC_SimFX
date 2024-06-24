/**
 * @author Sven Ibe
 * @version
 */

package com.example.mc_sim_data;

public class Welt {
    public static int random(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }

    private int genHolz;
    private int genStein;
    private int genEisen;

    public Welt() {
        genHolz = 0;
        genStein = 0;
        genEisen = 0;
        generieren();
    }

    public void generieren() {
        if (genHolz == 0) {
            genHolz = random(100, 200);
        }
        if (genStein == 0) {
            genStein = random(50, 100);
        }
        if (genEisen == 0) {
            genEisen = random(10, 20);
        }
        System.out.println("[WORLD] World Generation");
        System.out.println("|| generiertes Holz: " + genHolz);
        System.out.println("|| generiertes Stein: " + genStein);
        System.out.println("|| generiertes Eisen: " + genEisen);
    }

    public void checkGeneration () {
        if (genHolz <= 0) {
            genHolz = 0;
        }
        if (genStein <= 0) {
            genStein = 0;
        }
        if (genEisen <= 0) {
            genEisen = 0;
        }
    }

    public void subHolz(int pHolz) {
        genHolz -= pHolz;
    }

    public void subStein(int pStein) {
        genStein -= pStein;
    }

    public void subEisen(int pEisen) {
        genEisen -= pEisen;
    }


    /* Getters and Setters */
    public int getGenHolz() {
        return genHolz;
    }

    public int getGenStein() {
        return genStein;
    }

    public int getGenEisen() {
        return genEisen;
    }
}