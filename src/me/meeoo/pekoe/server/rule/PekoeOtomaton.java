/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.meeoo.pekoe.server.rule;

import java.util.LinkedList;
import java.util.List;
import me.meeoo.otomaton.automata.Otomaton;
import me.meeoo.otomaton.automata.State;
import me.meeoo.otomaton.automata.Transition;
import me.meeoo.pekoe.server.PekoeGame;
import me.meeoo.pekoe.server.PekoePlayer;

public class PekoeOtomaton extends Otomaton<PekoeGame> {

    private final LinkedList<PlayerTurn> playerTurns;
    private final State gameLobby;
    private final Transition newGame;
    private final Transition newTurn;
    private final State gameEnd;

    public PekoeOtomaton(List<PekoePlayer> players) {
        gameLobby = addState("Game start");
        gameEnd = addState("Game ends");

        playerTurns = new LinkedList<>();
        for (PekoePlayer player : players) {
            PlayerTurn playerTurn = new PlayerTurn(this, player);
            PlayerTurn previous_playerTurn = playerTurns.peekLast();
            playerTurns.add(playerTurn);

            if (previous_playerTurn != null) {
                previous_playerTurn.linkToNextPlayer(this, playerTurn);
            }
        }
        final PlayerTurn firstPlayerTurn = playerTurns.peekFirst();

        newTurn = playerTurns.peekLast().linkToNextPlayer(this, firstPlayerTurn);
        newGame = addTransition(gameLobby, firstPlayerTurn.start);

    }

    public boolean isStarted() {
        return getCurrentState().equals(gameLobby);
    }

    private class PlayerTurn {

        private final State start;
        private final State chooseSecondCard;
        private final State endTurn;
        
        private final Transition playSetOfCards;
        private final Transition endGame;
        private final Transition drawFirstCardVisible;
        private final Transition drawFirstCardHidden;
        private final Transition drawSecondCardHidden;
        private final Transition drawSecondCardVisible;

        PlayerTurn(Otomaton<PekoeGame> o, PekoePlayer p) {
            start = o.addState(String.format("Player turn - %s", p.getName()));
            chooseSecondCard = o.addState("Draw a second card");
            endTurn = o.addState("End turn");

            drawFirstCardHidden = o.addTransition(start, chooseSecondCard, new DrawHiddenCardCondition());
            drawFirstCardVisible = o.addTransition(start, chooseSecondCard, new DrawVisibleCardCondition());
            drawSecondCardHidden = o.addTransition(chooseSecondCard, endTurn, new DrawHiddenCardCondition());
            drawSecondCardVisible = o.addTransition(chooseSecondCard, endTurn, new DrawVisibleCardCondition());
            playSetOfCards =  o.addTransition(start, endTurn, new PlaySetOfCardCondition());
            endGame =  o.addTransition(endTurn, gameEnd, new EndGameCondition());

        }

        private Transition linkToNextPlayer(Otomaton<PekoeGame> o, PlayerTurn playerTurn) {
            return o.addTransition(endTurn, playerTurn.start);
        }

    }

}
