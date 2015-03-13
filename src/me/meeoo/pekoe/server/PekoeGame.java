/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.meeoo.pekoe.server;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import me.meeoo.otomaton.game.Game;
import me.meeoo.otomaton.game.Player;
import me.meeoo.otomaton.json.JSONable;
import me.meeoo.otomaton.json.JSONifier;
import me.meeoo.pekoe.server.rule.PekoeOtomaton;

public class PekoeGame extends Game<PekoeOtomaton, PekoePlayer> {

    private final int[] score_identical_nth = new int[]{0, 1, 3, 6, 10, 15, 21, 28};
    private final int[] score_different_nth = new int[]{0, 0, 2, 8, 24};

    private PekoeOtomaton rule;

    private final List<PekoeCard> cards;

    private PekoePlayer currentPlayer;
    private final List<PekoeCard> visibleCards;

    public PekoeGame(String name) {
        super(name);
        this.rule = null;
        this.cards = new LinkedList<>();
        this.visibleCards = new ArrayList<>(5);
    }

    public void startGame() {
        rule = new PekoeOtomaton(players);
        createPekoeCards();
        Utils.shuffle(cards);
    }

    private void createPekoeCards() {
        createPekoeCards(2, "White tea", Color.WHITE, 1);
        createPekoeCards(8, "Green tea", Color.GREEN, 2);
        createPekoeCards(6, "Blue tea", Color.BLUE, 4);
        createPekoeCards(8, "Red tea", Color.RED, 8);
        createPekoeCards(4, "Black tea", Color.BLACK, 16);
    }

    private void createPekoeCards(int count, String name, Color color, int type) {
        for (int i = 0 ; i < count ; i++) {
            cards.add(new PekoeCard(name, color, type));
        }
    }

    public PekoePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public List<PekoeCard> getCards() {
        return cards;
    }

    public List<PekoeCard> getVisibleCards() {
        return visibleCards;
    }

    public PekoeOtomaton getRule() {
        return rule;
    }

    @Override
    public PekoeOtomaton getOtomaton() {
        return rule;
    }

    public PekoeCard drawHidden(Player player) {
        PekoeCard card = cards.remove(cards.size() - 1); //get Last card
        ((PekoePlayer) player).getHand().add(card);
        return card;
    }

    public PekoeCard drawVisible(Player player, int cardToDraw) {
        PekoeCard card = visibleCards.get(cardToDraw);
        if (card != null) {
            ((PekoePlayer) player).getHand().add(card);
            if (cards.size() > 0) {
                PekoeCard newVisible = cards.remove(cards.size() - 1); //get Last card
                visibleCards.set(cardToDraw, newVisible);
            } else {
                visibleCards.set(cardToDraw, null);
            }
        }
        return card;
    }

    public int playSetOfCard(PekoePlayer p, LinkedList<PekoeCard> cardsToPlay) {
        int score = 0;
        int typeOfCardMask = 0;
        for (PekoeCard pekoeCard : cardsToPlay) {
            typeOfCardMask |= pekoeCard.getType();
        }
        if (cardsToPlay.size() == 5 && typeOfCardMask == 31) {//all type of cards are represented
            score = score_different_nth[4];
        }

        int differentCards = countBitsSet(typeOfCardMask);
        if (differentCards == 1) {//all the cards are identical
            score = score_identical_nth[cardsToPlay.size() - 1];
        } else if (differentCards == cardsToPlay.size()) {
            score = score_different_nth[cardsToPlay.size() - 1];
        }

        p.updateScore(score);
        return score;
    }

    /**
     * Brian Kernighan's counting bits set method
     * <p>
     * @param v the integer to count the bits of
     * @return the count of bits set (1) in v
     */
    private int countBitsSet(int v) {
        int count = 0;
        for (; v != 0 ; count++) {
            v &= v - 1; // clear the least significant bit set
        }
        return count;
    }

    public boolean isStarted() {
        if (rule == null) {
            return false;
        }
        return rule.isStarted();
    }

    @Override
    public StringBuilder toJSON(StringBuilder sb) {
        sb.append('{');

        sb.append("\"id\":");
        JSONifier.toJSON(sb, getId());

        sb.append(",\"name\":");
        JSONifier.toJSON(sb, getName());

        sb.append(",\"visualHash\":");
        JSONifier.toJSON(sb, getVisualHash());

        sb.append(",\"cards\":");
        JSONifier.toJSON(sb, getCards());

        sb.append(",\"players\":");
        JSONifier.toJSON(sb, getPlayers());

        sb.append(",\"rule\":");
        JSONifier.toJSON(sb, getRule());

        sb.append('}');
        return sb;
    }

}
