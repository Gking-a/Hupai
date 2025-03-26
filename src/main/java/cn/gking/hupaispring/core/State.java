package cn.gking.hupaispring.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class State {
    RoomConfig roomConfig;
    Player currentPlayer,lastPlayer;
    List<Card> stackCards;
    List<Card> topCards;
    List<Player> players;
    List<ClientStateChange> changes=new LinkedList<>();
    int claim;
    int step;
    public State(RoomConfig roomConfig) {
        this.roomConfig = roomConfig;
    }
    void initState(List<Player> players){}
}
