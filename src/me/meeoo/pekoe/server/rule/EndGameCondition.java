/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.meeoo.pekoe.server.rule;

import me.meeoo.otomaton.automata.TransitionCondition;
import me.meeoo.otomaton.event.Event;
import me.meeoo.pekoe.server.PekoeCard;
import me.meeoo.pekoe.server.PekoeGame;

public class EndGameCondition extends TransitionCondition<PekoeGame> {

    public static final int MIN_CARD_BEFORE_ENDGAME = 5;

    @Override
    public boolean isValid(PekoeGame game, Event event) {
        int total_cards_remaining = game.getCards().size();
        if (total_cards_remaining > MIN_CARD_BEFORE_ENDGAME) {
            return true;
        }
        //count remaining visible card
        for (PekoeCard pekoeCard : game.getVisibleCards()) {
            if (pekoeCard != null) {
                total_cards_remaining++;
            }
        }
        return total_cards_remaining > MIN_CARD_BEFORE_ENDGAME;
    }

}
