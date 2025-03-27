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
    public void solve(int actionType, List<Card>ClientCards){
        //传参运算
        AbstractStateChange RulesReturn = Rules.action(gameState, actionType);

        Player copyCurrentPlayer = gameState.currentPlayer;       //copy
        //turn to player solve & update current player
        if (RulesReturn.getTurn_to_player() == Flag.NEXT_PLAYER) {
            gameState.currentPlayer = players.get((players.indexOf(gameState.currentPlayer) + 1) % config.numberPlayer);
        } else if (RulesReturn.getTurn_to_player() == Flag.LAST_POKER) {
            gameState.currentPlayer = gameState.lastPlayer;
        } else if (RulesReturn.getTurn_to_player() == Flag.DEFAULT_NO_CHANGE) {
            endGame(gameState.lastPlayer);
            return;
        }

        //poke to player solve
        if(RulesReturn.getPoke_to_player()==Flag.LAST_POKER){
            //stackcards to last player
            for(Card it:gameState.stackCards){
                gameState.lastPlayer.cards.add(it);
                gameState.lastPlayer.cardnum++;
            }
            gameState.stackCards.clear();

            //topcards to last player
            for(Card it:gameState.topCards){
                gameState.lastPlayer.cards.add(it);
                gameState.lastPlayer.cardnum++;
            }
            gameState.topCards.clear();
        }else if(RulesReturn.getPoke_to_player()==Flag.CHALLENGER){
            //stackcards to challenge player
            for(Card it:gameState.stackCards){
                copyCurrentPlayer.cards.add(it);
                copyCurrentPlayer.cardnum++;
            }
            gameState.stackCards.clear();

            //topcards to challenge player
            for(Card it:gameState.topCards){
                copyCurrentPlayer.cards.add(it);
                copyCurrentPlayer.cardnum++;
            }
            gameState.topCards.clear();
        }else if(RulesReturn.getPoke_to_player()==Flag.QUIT){
            gameState.topCards.clear();
            gameState.stackCards.clear();
        }else{
            //Flag.DEFAULT_NO_CHANGE
            for(Card it:gameState.topCards)
                gameState.stackCards.add(it);
            gameState.topCards.clear();
        }

        //extra follow judge(update topCards)
        if(actionType==Flag.ACTION_FOLLOW){
            gameState.topCards=ClientCards;
        }

        //update last player
        if (actionType == Flag.ACTION_CHALLENGE) {
            gameState.lastPlayer = null;
        } else if (actionType == Flag.ACTION_FOLLOW) {
            gameState.lastPlayer = copyCurrentPlayer;
        }

        gameState.step++;
    }
    private void endGame(Player winner){

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
