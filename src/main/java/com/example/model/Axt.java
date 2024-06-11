/**
 * @author Sven Ibe
 * @version
 */

package com.example.model;

public class Axt extends Werkzeug {
    public Axt(String material) {
        super(material);
        berechneWert();
    }

    @Override
    public void berechneWert() {
        switch (material) {
            case "Holz":
                wert = 2;
                break;
            case "Stein":
                wert = 3;
                break;
            case "Eisen":
                wert = 6;
                break;
            case "none":
                wert = 0;
                break;
            default:
                System.out.println("falsches Material wurde angegeben!");
                break;
        }
    }
}
