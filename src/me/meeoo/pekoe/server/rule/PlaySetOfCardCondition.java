/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.meeoo.pekoe.server.rule;

import java.util.LinkedList;
import me.meeoo.otomaton.automata.TransitionCondition;
import me.meeoo.otomaton.event.Event;
import me.meeoo.otomaton.event.NotPlayerTurnFailStatus;
import me.meeoo.pekoe.server.PekoeCard;
import me.meeoo.pekoe.server.PekoeGame;
import me.meeoo.pekoe.server.PekoePlayer;
import me.meeoo.pekoe.server.event.PlaySetOfCardEvent;

public class PlaySetOfCardCondition extends TransitionCondition<PekoeGame> {

    @Override
    public boolean isValid(PekoeGame game, Event event) {
        if (event instanceof PlaySetOfCardEvent) {
            PlaySetOfCardEvent e = (PlaySetOfCardEvent) event;
            final PekoePlayer currentPlayer = game.getCurrentPlayer();
            if (e.getPlayer(game).equals(currentPlayer) == false) {
                event.setFailStatus(new NotPlayerTurnFailStatus());
                return false;
            }
            LinkedList<PekoeCard> hand = currentPlayer.getHand();
            String name = null;
            int differentNames = 1;
            for (long cardId : e.getCardIds()) {
                boolean found = false;
                for (PekoeCard cardInHand : hand) {
                    if (cardInHand.getId() == cardId) {
                        found = true;
                        if (name == null) {
                            name = cardInHand.getName();
                        } else {
                            if (false == name.equals(cardInHand.getName())) {
                                differentNames++;
                            }
                        }
                        break;
                    }
                }
                if (found == false) {
                    return false;
                }
            }
            if (differentNames == 1 || differentNames == e.getCardIds().size()) {
                return true;
            }
            return false;
        }
        return false;
    }

}
