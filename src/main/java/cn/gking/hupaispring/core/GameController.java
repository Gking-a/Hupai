package cn.gking.hupaispring.core;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    RoomConfig config;
    public List<Player> players= new ArrayList<>();
    State gameState;
    DisposeCallback disposeCallback;
    public GameController(RoomConfig config){}
    public void setDisposeCallback(DisposeCallback disposeCallback){
        this.disposeCallback=disposeCallback;
    }
    interface DisposeCallback {
        void onFinish(GameController gameController);
    }
    void challenge(Player p){
        AbstractStateChange action = Rules.action(gameState, Flag.ACTION_CHALLENGE);

    }
}
