/**
 * @author Sven Ibe
 * @version
 */

package com.example.mc_sim_data;

public class Spiel {
    private final Welt world;
    private final Spieler player;

    public Spiel (int worldGen_min, int worldGen_max) {
        world = new Welt (worldGen_min, worldGen_max);
        player = new Spieler (10, world);
    }

    public Welt getWorld() {
        return world;
    }

    public Spieler getPlayer() {
        return player;
    }
}
