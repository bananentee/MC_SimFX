/**
 * @author Sven Ibe
 * @version
 */

package com.example.mc_sim_data;

public class Spiel {

    private Welt world;
    private Spieler player;

    public void play() {
        world = new Welt ();
        player = new Spieler ("Steve",10, world);

        System.out.println("---------------------------");
        world.generieren();
        System.out.println("---------------------------");
        world.showMat();
        System.out.println("---------------------------");
        player.showInv();
        System.out.println("---------------------------");
    }

    public Welt getWorld() {
        return world;
    }

    public Spieler getPlayer() {
        return player;
    }
}
