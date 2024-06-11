/**
 * @author Sven Ibe
 * @version
 */

package com.example.mc_sim_data;

public abstract class Werkzeug {

    protected int wert;
    protected String material;
    public Werkzeug (String material) {
        this.material = material;
    }

    public abstract void berechneWert ();
    public String getMaterial () {
        return material;
    }

    public int getWert() {
        return wert;
    }

}
