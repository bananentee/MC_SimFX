/**
 * @author Sven Ibe
 * @version
 */

package com.example.model;

public class Welt {

    public static int random (int min, int max) {
        int range = max - min + 1;
        return (int)(Math.random() * range) + min;
    }

    private int genHolz;
    private int genStein;
    private int genEisen;

    public Welt () {
        genHolz = random(10,100);
        genStein = random(50,100);
        genEisen = random(1,20);
    }
    public void showMat () {
        System.out.println("generiertes Holz: " + genHolz);
        System.out.println("generiertes Stein: " + genStein);
        System.out.println("generiertes Eisen: " + genEisen);
    }
    public void generieren () {
        if (genHolz == 0) {
            genHolz = random(10,100);
        } else if (genStein == 0) {
            genStein = random(50,100);
        } else if (genEisen == 0) {
            genEisen = random(1, 20);
        }
        showMat();
    }

    public void subHolz (int pHolz) {
        genHolz -= pHolz;
    }

    public void subStein (int pStein) {
        genStein -= pStein;
    }

    public void subEisen (int pEisen) {
        genEisen -= pEisen;
    }

    public void resetMat () {
        genHolz = 0;
        genStein = 0;
        genEisen = 0;
    }
}

