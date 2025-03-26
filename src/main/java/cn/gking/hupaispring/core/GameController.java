package cn.gking.hupaispring.core;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    public RoomConfig config;
    public List<Player> players= new ArrayList<>();
    public State gameState;
    DisposeCallback disposeCallback;
    public GameController(RoomConfig config){
        this.config=config;
        gameState=new State(config);
        gameState.changes.add(new ClientStateChange());
    }

    public String getStep(int step) {
        return gameState.changes.get(step).toString();
    }

    public String lastChange() {
        return gameState.changes.get(gameState.changes.size()-1).toString();
    }

    public interface DisposeCallback {
        void onFinish(GameController gameController);
    }
    public void solve(int actionType){
        AbstractStateChange RulesReturn = Rules.action(gameState, actionType);
    }
    public void startGame(DisposeCallback disposeCallback){
        this.disposeCallback=disposeCallback;
        registerPlayer();
    }
    private void registerPlayer() {
        stepForward();
        gameState.players=this.players;
        players.forEach(player -> player.position--);
        Dealer dealer = new Dealer(gameState.roomConfig.numberPoke,gameState.roomConfig.numberJoker,gameState.roomConfig.numberPlayer,gameState.roomConfig.numberQuit);
        List<List<Card>> finalPoke = dealer.getFinalPoke();
        for (int i = 0; i < finalPoke.size(); i++) {
            gameState.players.get(i).addCards(finalPoke.get(i));
        }
        ClientStateChange firstChange = new ClientStateChange.Builder()
                .step(gameState.step)
                .action(Flag.REGISTER_PLAYER)
                .build();
        firstChange.rp=gameState.players;
        gameState.changes.add(firstChange);
    }
    //Any step change function should call this method first
    public void stepForward(){
        gameState.step++;
    }
}
