package cn.gking.hupaispring.core;

import org.apache.tomcat.util.digester.Rule;

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
    public void solve(int actionType, List<Card>ClientCards,int reclaim){
        //防空参
        if(ClientCards==null){
            ClientCards=new ArrayList<>();
        }
        ClientStateChange toClient = new ClientStateChange.Builder().cardNum(0).build();
        //传参运算
        AbstractStateChange RulesReturn = Rules.action(gameState, actionType);
        System.out.println(1);
        System.out.println(RulesReturn);System.out.println(gameState);
        Player copyCurrentPlayer = gameState.currentPlayer;       //copy
        Player copyLastPlayer = gameState.lastPlayer;             //copy
        //turn to player solve & update current player
        if (RulesReturn.getTurn_to_player() == Flag.NEXT_PLAYER) {
            gameState.currentPlayer = players.get((players.indexOf(gameState.currentPlayer) + 1) % config.numberPlayer);
        } else if (RulesReturn.getTurn_to_player() == Flag.LAST_POKER) {
            gameState.currentPlayer = gameState.lastPlayer;
        } else if (RulesReturn.getTurn_to_player() == Flag.DEFAULT_NO_CHANGE) {
            endGame();
            return;
        }
        System.out.println(2);
        System.out.println(RulesReturn);System.out.println(gameState);
        //poke to player solve
        if(RulesReturn.getPoke_to_player()==Flag.LAST_POKER){
            //stackcards to last player
            gameState.lastPlayer.cards.addAll(gameState.stackCards);
            gameState.lastPlayer.cardnum+=gameState.stackCards.size();
            gameState.stackCards.clear();
            //topcards to last player
            gameState.lastPlayer.cards.addAll(gameState.topCards);
            gameState.lastPlayer.cardnum+=gameState.topCards.size();
            gameState.topCards.clear();
        }else if(RulesReturn.getPoke_to_player()==Flag.CHALLENGER){
            //stackcards to challenge player
            copyCurrentPlayer.cards.addAll(gameState.stackCards);
            copyCurrentPlayer.cardnum+=gameState.stackCards.size();
            gameState.stackCards.clear();
            //topcards to challenge player
            copyCurrentPlayer.cards.addAll(gameState.topCards);
            copyCurrentPlayer.cardnum+=gameState.topCards.size();
            gameState.topCards.clear();
        }else if(RulesReturn.getPoke_to_player()==Flag.QUIT){
            //cardnum update
            toClient.setCardNum(gameState.topCards.size()+gameState.stackCards.size());
            gameState.topCards.clear();
            gameState.stackCards.clear();
        }else{
            //Flag.DEFAULT_NO_CHANGE
            if(ClientCards.size()!=0) {
                gameState.stackCards.addAll(gameState.topCards);
                gameState.topCards.clear();
                if(actionType==Flag.ACTION_RECLAIM){
                    gameState.topCards=ClientCards;
                }
            }
        }
        System.out.println(3);
        System.out.println(RulesReturn);System.out.println(gameState);
        //extra follow judge(update topCards)
        if(actionType==Flag.ACTION_FOLLOW){
            gameState.topCards=ClientCards;
            copyCurrentPlayer.cardnum-=ClientCards.size();
        }
        System.out.println(4);
        System.out.println(RulesReturn);System.out.println(gameState);
        //update last player
        if (actionType == Flag.ACTION_CHALLENGE) {
            gameState.lastPlayer = null;
        } else if (actionType == Flag.ACTION_FOLLOW) {
            gameState.lastPlayer = copyCurrentPlayer;
        }else if(actionType==Flag.ACTION_RECLAIM){
            gameState.lastPlayer=copyCurrentPlayer;
        }
        System.out.println(5);
        System.out.println(RulesReturn);System.out.println(gameState);
        gameState.step++;
        if(actionType==Flag.ACTION_FOLLOW){
            //xyp think it's necessary
            copyCurrentPlayer.cardnum-=ClientCards.size();
            toClient.setTurn_to_player(gameState.players.indexOf(gameState.currentPlayer));
            toClient.setPoke_to_player(-1);
            //no poke to player
            toClient.setAction(actionType);
            toClient.setStep(gameState.step);
            //xyp:FOLLOW => cardnum=跟牌数
            toClient.setCardNum(ClientCards.size());
            toClient.setActiveCards(null);
        }else if(actionType==Flag.ACTION_PASS){
            toClient.setTurn_to_player(gameState.players.indexOf(gameState.currentPlayer));
            toClient.setPoke_to_player(-1);
            //no poke to player(include poke quit state)
            toClient.setAction(actionType);
            toClient.setStep(gameState.step);
            toClient.setActiveCards(null);
        }else if(actionType==Flag.ACTION_RECLAIM){
            toClient=new ClientStateChange.Builder()
                    .step(gameState.step)
                    .action(Flag.ACTION_RECLAIM)
                    .cardNum(ClientCards.size())
                    .pokeToPlayer(reclaim)
                    .turnToPlayer(gameState.players.indexOf(gameState.currentPlayer))
                    .build();
        }
        else{
            //Flag.ACTION_CHALLENGE
            toClient.setTurn_to_player(gameState.players.indexOf(gameState.currentPlayer));
            if(RulesReturn.getPoke_to_player()==Flag.CHALLENGER){
                toClient.setPoke_to_player(gameState.players.indexOf(copyCurrentPlayer));
            }
            else{
                toClient.setPoke_to_player(gameState.players.indexOf(copyLastPlayer));
            }
            toClient.setAction(actionType);
            toClient.setStep(gameState.step);
            List<Card>activeCards=new ArrayList<>(gameState.stackCards);
            activeCards.addAll(gameState.topCards);
            toClient.setActiveCards(activeCards);
            toClient.setCardNum(toClient.getActiveCards().size());
        }System.out.println(6);
        System.out.printf("%d %d~\n",toClient.turn_to_player,toClient.poke_to_player);
        System.out.println(RulesReturn);System.out.println(gameState);
        gameState.changes.add(toClient);
    }
    private void endGame(){
        /*
        *
        * endGame状态传参说明：
        * 传出的ClientStateChange中step更新，rp赋值，turn_to_player及poke_to_player及action赋值Flag.GAME_END
        * 其余均不用管
        *
         */
        gameState.step++;
        ClientStateChange toClient = new ClientStateChange();

        int winner=0;
        for(;gameState.players.get(winner).cards.size()!=0;winner++){
            if(winner==gameState.roomConfig.numberPlayer){
                break;
            }
        }
        toClient.setTurn_to_player(winner);

        toClient.setStep(gameState.step);
        toClient.setAction(Flag.GAME_END);
        gameState.changes.add(toClient);
    }
    public void startGame(DisposeCallback disposeCallback){
        this.disposeCallback=disposeCallback;
        registerPlayer();
        gameState.currentPlayer=gameState.players.get(0);
        gameState.topCards=new ArrayList<>();
        gameState.stackCards=new ArrayList<>();
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
