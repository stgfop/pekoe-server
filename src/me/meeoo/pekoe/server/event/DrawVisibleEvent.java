/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.meeoo.pekoe.server.event;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import me.meeoo.otomaton.game.PlayerAction;
import me.meeoo.pekoe.server.PekoeCard;
import me.meeoo.pekoe.server.PekoeGame;
import me.meeoo.pekoe.server.PekoePlayer;

public class DrawVisibleEvent extends PlayerAction<PekoeGame, PekoePlayer> {

    private int cardToDraw;
    private PekoeCard cardDrawn;

    public DrawVisibleEvent(int cardToDraw) {
        this.cardToDraw = cardToDraw;
    }

    public long getCardToDraw() {
        return cardToDraw;
    }

    @Override
    public boolean execute(PekoeGame game) {
        cardDrawn = game.drawVisible(getPlayer(game), cardToDraw);

        return cardDrawn != null;
    }

    @Override
    protected void sendEvent(DataOutput out) throws IOException {
        out.writeLong(cardDrawn.getId());
    }

    @Override
    protected void readEvent(DataInput in) throws IOException {
        cardToDraw = in.readInt();
    }

}
