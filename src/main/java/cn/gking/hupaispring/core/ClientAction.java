package cn.gking.hupaispring.core;

import java.util.List;

public class ClientAction {
    int action;
    List<Card> cards;
    int roomid;
    public int reclaim;

    public int getReclaim() {
        return reclaim;
    }

    public void setReclaim(int reclaim) {
        this.reclaim = reclaim;
    }

    public ClientAction(int action, List<Card> cards, int roomid, int reclaim) {
        this.action = action;
        this.cards = cards;
        this.roomid = roomid;
        this.reclaim = reclaim;
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

    @Override
    public String toString() {
        return "ClientAction{" +
                "action=" + action +
                ", cards=" + cards +
                ", roomid=" + roomid +
                ", reclaim=" + reclaim +
                '}';
    }
}
