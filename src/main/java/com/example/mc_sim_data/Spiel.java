/**
 * @author Sven Ibe
 * @version
 */

package com.example.mc_sim_data;

public class Spiel {
    //TODO Adding of real meaningful gameplay!
    private final Welt world;
    private final Spieler player;

    public Spiel () {
        world = new Welt ();
        player = new Spieler (10, world);
    }

    public Welt getWorld() {
        return world;
    }

    public Spieler getPlayer() {
        return player;
    }
}
