package cn.gking.hupaispring.core;

import java.util.ArrayList;
import java.util.List;

public class State {
    RoomConfig roomConfig;
    Player currentPlayer,lastPlayer;
    List<Card> stackCards;
    List<Card> topCards;
    List<Player> players;
    int claim;
}
