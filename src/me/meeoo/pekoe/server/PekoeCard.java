/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.meeoo.pekoe.server;

import java.awt.Color;
import me.meeoo.otomaton.game.GameObject;


public class PekoeCard extends GameObject {
    private final Color color;
    private final int type;

    public PekoeCard(String name, Color color, int type) {
        super(name);
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public int getType() {
        return type;
    }

}
