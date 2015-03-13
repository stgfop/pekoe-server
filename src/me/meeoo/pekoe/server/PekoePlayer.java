/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.meeoo.pekoe.server;

import java.util.LinkedList;
import me.meeoo.otomaton.game.Player;
import me.meeoo.otomaton.json.JSONifier;


public class PekoePlayer extends Player {

    private final LinkedList<PekoeCard> hand;
    private int score;
   
    public PekoePlayer(String name) {
        super(name);
        this.hand = new LinkedList<>();
        this.score = 0;
    }
 
    public LinkedList<PekoeCard> getHand() {
        return hand;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int delta) {
        this.score += delta;
    }

    @Override
    public StringBuilder toJSON(StringBuilder sb) {
        sb.append('{');
        
        sb.append("\"id\":");
        JSONifier.toJSON(sb, getId());
        
        sb.append(",\"name\":");
        JSONifier.toJSON(sb, getName());
      
        sb.append(",\"score\":");
        JSONifier.toJSON(sb, getScore());
    
        sb.append(",\"hand\":");
        JSONifier.toJSON(sb, getHand());
        
        sb.append('}');
        return sb;
    }

    

    

}
