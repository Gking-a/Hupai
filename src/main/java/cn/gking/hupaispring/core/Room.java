package cn.gking.hupaispring.core;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Room {
    RoomConfig config;
    List<Player> players= new ArrayList<>();
    State gameState;
    public Room(RoomConfig config){}
}
