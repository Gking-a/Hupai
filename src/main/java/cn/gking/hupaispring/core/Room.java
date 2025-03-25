package cn.gking.hupaispring.core;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Room {
    RoomConfig config;
    List<Player> players= new ArrayList<>();
    Room(RoomConfig config, ServerSocket serverSocket){}
}
