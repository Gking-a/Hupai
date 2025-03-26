package cn.gking.hupaispring.core;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    public RoomConfig config;
    public List<Player> players= new ArrayList<>();
    public State gameState;
    DisposeCallback disposeCallback;
    public GameController(RoomConfig config){}
    public void setDisposeCallback(DisposeCallback disposeCallback){
        this.disposeCallback=disposeCallback;
    }
    interface DisposeCallback {
        void onFinish(GameController gameController);
    }

    //下面为action解析
    public void solve(int actionType){
        AbstractStateChange RulesReturn=Rules.action(gameState,actionType);

    }
}
