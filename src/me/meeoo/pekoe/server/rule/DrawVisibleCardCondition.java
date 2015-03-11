/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.meeoo.pekoe.server.rule;

import me.meeoo.otomaton.automata.TransitionCondition;
import me.meeoo.otomaton.event.Event;
import me.meeoo.otomaton.event.NotPlayerTurnFailStatus;
import me.meeoo.pekoe.server.PekoeGame;
import me.meeoo.pekoe.server.event.DrawVisibleEvent;

public class DrawVisibleCardCondition extends TransitionCondition<PekoeGame> {

    @Override
    public boolean isValid(PekoeGame game, Event event) {
        if (event instanceof DrawVisibleEvent) {
            DrawVisibleEvent e = (DrawVisibleEvent) event;
            if (e.getPlayer(game).equals(game.getCurrentPlayer())) {
                if (null != game.getVisibleCards().get((int) e.getCardToDraw())) {
                    return true;
                }
            } else {
                event.setFailStatus(new NotPlayerTurnFailStatus());
                return false;
            }
        }
        return false;
    }

}
