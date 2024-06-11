/**
 * @author Sven Ibe
 * @version
 */

package com.example.mc_sim_data;

public class Spiel {

    public static void main(String[] args) {
        Welt welt = new Welt ();
        Spieler spieler = new Spieler ("Steve",10, welt);

        System.out.println("---------------------------");
        welt.generieren();
        System.out.println("---------------------------");
        spieler.abbauen();
        System.out.println("---------------------------");
        welt.showMat();
        System.out.println("---------------------------");
        spieler.showInv();
        System.out.println("---------------------------");
        spieler.abbauen();
        System.out.println("---------------------------");
        welt.showMat();
    }


}
