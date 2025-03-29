package cn.gking.hupaispring.core;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    public RoomConfig config;
    public List<Player> players= new ArrayList<>();
    public State gameState;
    public DisposeCallback disposeCallback;
    public long timestamp;
    public GameController(RoomConfig config){
        this.config=config;
        gameState=new State(config);
        gameState.changes.add(new ClientStateChange());
        timestamp=System.currentTimeMillis();
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
    public void solve(int actionType, List<Card>ClientCards, int reclaim){
        //防空参
        if(ClientCards==null){
            ClientCards=new ArrayList<>();
        }
        ClientStateChange toClient = new ClientStateChange();
        //传参运算
        //AbstractStateChange RulesReturn = Rules.action(gameState, actionType);

        //对任何action，gameState要改变currentPlayer,lastPlayer,stackCards,topCards,claim,players.cardNum,step并存入changes
        if(actionType==Flag.ACTION_RECLAIM){
            removeAllCard(gameState.currentPlayer.cards,ClientCards);
            //判断endGame
            if(gameState.currentPlayer.cards.isEmpty()){
                boolean ifEndGame=true;
                for(Card it:ClientCards){
                    if(it.getRank()!=reclaim&&it.getSuit()!=Flag.CARD_JOKER){
                        ifEndGame=false;
                        break;
                    }
                }
                if(ifEndGame){
                    endGame(gameState.currentPlayer.position);
                    return;
                }
            }
            gameState.lastPlayer=gameState.currentPlayer;
            gameState.currentPlayer.cardnum-=ClientCards.size();
            gameState.currentPlayer=gameState.players.get((gameState.currentPlayer.position+1)%gameState.roomConfig.numberPlayer);
            gameState.stackCards.clear();
            gameState.topCards.addAll(ClientCards);
            gameState.claim=reclaim;
            gameState.step++;

            toClient.setStep(gameState.step);
            toClient.setTurn_to_player(gameState.currentPlayer.position);
            toClient.setPoke_to_player(reclaim);
            toClient.setActiveCards(null);
            toClient.setAction(actionType);
            toClient.setCardNum(ClientCards.size());

            gameState.changes.add(toClient);
        }
        else if(actionType==Flag.ACTION_PASS){
            gameState.currentPlayer=gameState.players.get((gameState.currentPlayer.position+1)%gameState.roomConfig.numberPlayer);
            if(gameState.currentPlayer.equals(gameState.lastPlayer)){
                //QUIT
                gameState.lastPlayer=null;
                gameState.step++;

                toClient.setCardNum(gameState.topCards.size()+gameState.stackCards.size());
                toClient.setStep(gameState.step);
                toClient.setTurn_to_player(gameState.currentPlayer.position);
                toClient.setPoke_to_player(-2);
                toClient.setActiveCards(null);
                toClient.setAction(actionType);

                gameState.stackCards.clear();
                gameState.topCards.clear();

                gameState.changes.add(toClient);
            }
            else{
                //JUST PASS
                gameState.step++;
                toClient.setStep(gameState.step);
                toClient.setTurn_to_player(gameState.currentPlayer.position);
                toClient.setPoke_to_player(-1);
                toClient.setAction(actionType);
                toClient.setActiveCards(null);
                toClient.setCardNum(0);
                gameState.changes.add(toClient);
            }
        }
        else if(actionType==Flag.ACTION_FOLLOW){
            removeAllCard(gameState.currentPlayer.cards,ClientCards);
            //判断endGame
            if(gameState.currentPlayer.cards.isEmpty()){
                boolean ifEndGame=true;
                for(Card it:ClientCards){
                    if(it.getRank()!=reclaim&&it.getSuit()!=Flag.CARD_JOKER){
                        ifEndGame=false;
                        break;
                    }
                }
                if(ifEndGame){
                    endGame(gameState.currentPlayer.position);
                    return;
                }
            }
            gameState.currentPlayer.cardnum-=ClientCards.size();
            gameState.lastPlayer=gameState.currentPlayer;
            gameState.currentPlayer=gameState.players.get((gameState.currentPlayer.position+1)%gameState.roomConfig.numberPlayer);
            gameState.step++;
            gameState.stackCards.addAll(gameState.topCards);
            gameState.topCards=ClientCards;

            toClient.setStep(gameState.step);
            toClient.setTurn_to_player(gameState.currentPlayer.position);
            toClient.setPoke_to_player(-1);
            toClient.setAction(actionType);
            toClient.setActiveCards(null);
            toClient.setCardNum(ClientCards.size());

            gameState.changes.add(toClient);
        }
        else if(actionType==Flag.ACTION_CHALLENGE){
            boolean ifChallengerWin=false;
            for(Card it:gameState.topCards){
                if(it.getRank()!=gameState.claim&&it.getSuit()!=Flag.CARD_JOKER){
                    ifChallengerWin=true;
                    break;
                }
            }
            ClientStateChange showPokeCSC = new ClientStateChange.Builder()
                    .action(Flag.ACTION_SPECIAL_SHOW)
                    .setActiveCards(new ArrayList<>(gameState.topCards))
                    .build();
            if(ifChallengerWin){
                //challenger win
                //gameState.currentPlayer not change
                gameState.lastPlayer.cards.addAll(gameState.topCards);
                gameState.lastPlayer.cards.addAll(gameState.stackCards);
                gameState.lastPlayer.cardnum+=gameState.topCards.size()+gameState.stackCards.size();
                toClient.setPoke_to_player(gameState.lastPlayer.position);
            }
            else{
                //lastPlayer win
                gameState.currentPlayer.cards.addAll(gameState.topCards);
                gameState.currentPlayer.cards.addAll(gameState.stackCards);
                gameState.currentPlayer.cardnum+=gameState.topCards.size()+gameState.stackCards.size();

                toClient.setPoke_to_player(gameState.currentPlayer.position);

                gameState.currentPlayer=gameState.lastPlayer;
            }
            gameState.lastPlayer=null;
            gameState.step++;
            toClient.setTurn_to_player(gameState.currentPlayer.position);
            toClient.setStep(gameState.step);
            toClient.setAction(actionType);
            toClient.activeCards=new ArrayList<>(gameState.stackCards);
            toClient.activeCards.addAll(gameState.topCards);
            toClient.setCardNum(toClient.getActiveCards().size());
            gameState.changes.add(toClient);

            gameState.step++;
            showPokeCSC.step= gameState.step;
            gameState.changes.add(showPokeCSC);

            gameState.stackCards.clear();
            gameState.topCards.clear();

        }
        if(gameState.currentPlayer.cards.isEmpty()){
            endGame(gameState.currentPlayer.position);
        }
    }

    private void removeAllCard(List<Card> cards, List<Card> clientCards) {
        for (int i = 0; i < clientCards.size(); i++) {
            cards.remove(clientCards.get(i));
        }
    }

    private void endGame(int winner){
        /*
        *
        * endGame状态传参说明：
        * 传出的ClientStateChange中step更新，turn_to_player及poke_to_player及action赋值Flag.GAME_END
        * 其余均不用管
        *
         */
        gameState.step++;
        ClientStateChange toClient = new ClientStateChange();
        toClient.setTurn_to_player(winner);
        toClient.setStep(gameState.step);
        toClient.setAction(Flag.GAME_END);
        gameState.changes.add(toClient);
        if(this.disposeCallback!=null){
            new Thread(() -> {
                try {
                    Thread.sleep(30*1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.disposeCallback.onFinish(this);
            }).start();
        }
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
