package cn.gking.hupaispring.core;

import java.util.List;

public class ClientAction {
    int action;
    List<Card> cards;
    int roomid;
    String name;

    public ClientAction(int action, List<Card> cards, int roomid) {
        this.action = action;
        this.cards = cards;
        this.roomid = roomid;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
