package cn.gking.hupaispring.core;

import java.util.List;

public class ClientStateChange {
    int turn_to_player;
    int poke_to_player;;
    List<Card> activeCards;
    int action;
    int cardNum;
    int step;

    public int getTurn_to_player() {
        return turn_to_player;
    }

    public void setTurn_to_player(int turn_to_player) {
        this.turn_to_player = turn_to_player;
    }

    public int getPoke_to_player() {
        return poke_to_player;
    }

    public void setPoke_to_player(int poke_to_player) {
        this.poke_to_player = poke_to_player;
    }

    public List<Card> getActiveCards() {
        return activeCards;
    }

    public void setActiveCards(List<Card> activeCards) {
        this.activeCards = activeCards;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }
}
