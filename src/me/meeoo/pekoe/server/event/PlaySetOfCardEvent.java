/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.meeoo.pekoe.server.event;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import me.meeoo.otomaton.game.PlayerAction;
import me.meeoo.pekoe.server.PekoeCard;
import me.meeoo.pekoe.server.PekoeGame;
import me.meeoo.pekoe.server.PekoePlayer;
import me.meeoo.pekoe.server.Utils;

public class PlaySetOfCardEvent extends PlayerAction<PekoeGame, PekoePlayer>  {

    private List<Long> cardIds;
    private int score;

    public PlaySetOfCardEvent(List<Long> cards) {
        this.cardIds = cards;
    }

    public List<Long> getCardIds() {
        return cardIds;
    }

    @Override
    public boolean execute(PekoeGame game) {
        PekoePlayer player = getPlayer(game);
        LinkedList<PekoeCard> cardsToPlay = new LinkedList<>();
        LinkedList<PekoeCard> hand = player.getHand();
        for (Long idToPlay : cardIds) {
            for (int i = hand.size() - 1 ; i >= 0 ; i--) {
                PekoeCard cardInHand = hand.get(i);
                if (cardInHand.getId() == idToPlay) {
                    cardsToPlay.add(cardInHand);
                    player.getHand().remove(i);
                }
            }

        }
        score = game.playSetOfCard(player, cardsToPlay);
        if (score == 0) {
            hand.addAll(cardsToPlay); //put them back;
        }
        return score > 0;
    }

    @Override
    protected void sendEvent(DataOutput out) throws IOException {
        out.writeInt(score);
    }

    @Override
    protected void readEvent(DataInput in) throws IOException {
        cardIds = Utils.readListLong(in);
    }

    
    
}

