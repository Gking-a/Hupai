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
//    public void solve(int actionType, List<Card>ClientCards,int reclaim){
//        //防空参
//        if(ClientCards==null){
//            ClientCards=new ArrayList<>();
//        }
//        ClientStateChange toClient = new ClientStateChange.Builder().cardNum(0).build();
//        //传参运算
//        AbstractStateChange RulesReturn = Rules.action(gameState, actionType);
//        System.out.println(1);
//        System.out.println(RulesReturn);
//        System.out.println(gameState);

//        Player copyCurrentPlayer = gameState.currentPlayer;       //copy
//        Player copyLastPlayer = gameState.lastPlayer;             //copy

//        //turn to player solve & update current player
//        if (RulesReturn.getTurn_to_player() == Flag.NEXT_PLAYER) {
//            gameState.currentPlayer = players.get((players.indexOf(gameState.currentPlayer) + 1) % config.numberPlayer);
//        } else if (RulesReturn.getTurn_to_player() == Flag.LAST_POKER) {
//            gameState.currentPlayer = gameState.lastPlayer;
//        } else if (RulesReturn.getTurn_to_player() == Flag.DEFAULT_NO_CHANGE) {
//            endGame();
//            return;
//        }

//        System.out.println(2);
//        System.out.println(RulesReturn);System.out.println(gameState);

//        //poke to player solve
//        if(RulesReturn.getPoke_to_player()==Flag.LAST_POKER){
//            //stackcards to last player
//            gameState.lastPlayer.cards.addAll(gameState.stackCards);
//            gameState.lastPlayer.cardnum+=gameState.stackCards.size();
//            gameState.stackCards.clear();
//            //topcards to last player
//            gameState.lastPlayer.cards.addAll(gameState.topCards);
//            gameState.lastPlayer.cardnum+=gameState.topCards.size();
//            gameState.topCards.clear();
//        }else if(RulesReturn.getPoke_to_player()==Flag.CHALLENGER){
//            //stackcards to challenge player
//            copyCurrentPlayer.cards.addAll(gameState.stackCards);
//            copyCurrentPlayer.cardnum+=gameState.stackCards.size();
//            gameState.stackCards.clear();
//            //topcards to challenge player
//            copyCurrentPlayer.cards.addAll(gameState.topCards);
//            copyCurrentPlayer.cardnum+=gameState.topCards.size();
//            gameState.topCards.clear();
//        }else if(RulesReturn.getPoke_to_player()==Flag.QUIT){
//            //cardnum update
//            toClient.setCardNum(gameState.topCards.size()+gameState.stackCards.size());
//            gameState.topCards.clear();
//            gameState.stackCards.clear();
//        }else{
//            //Flag.DEFAULT_NO_CHANGE
//            if(ClientCards.size()!=0) {
//                gameState.stackCards.addAll(gameState.topCards);
//                gameState.topCards.clear();
//                if(actionType==Flag.ACTION_RECLAIM){
//                    gameState.topCards=ClientCards;
//                }
//            }
//        }
//        System.out.println(3);
//        System.out.println(RulesReturn);System.out.println(gameState);

//        //extra follow judge(update topCards)
//        if(actionType==Flag.ACTION_FOLLOW){
//            gameState.topCards=ClientCards;
//            copyCurrentPlayer.cardnum-=ClientCards.size();
//        }

//        System.out.println(4);
//        System.out.println(RulesReturn);System.out.println(gameState);

//        //update last player
//        if (actionType == Flag.ACTION_CHALLENGE) {
//            gameState.lastPlayer = null;
//        } else if (actionType == Flag.ACTION_FOLLOW) {
//            gameState.lastPlayer = copyCurrentPlayer;
//        }else if(actionType==Flag.ACTION_RECLAIM){
//            gameState.lastPlayer=copyCurrentPlayer;
//        }

//        System.out.println(5);
//        System.out.println(RulesReturn);System.out.println(gameState);
//        gameState.step++;

//        if(actionType==Flag.ACTION_FOLLOW){
//            //xyp think it's necessary
//            copyCurrentPlayer.cardnum-=ClientCards.size();
//            toClient.setTurn_to_player(gameState.players.indexOf(gameState.currentPlayer));
//            toClient.setPoke_to_player(-1);
//            //no poke to player
//            toClient.setAction(actionType);
//            toClient.setStep(gameState.step);
//            //xyp:FOLLOW => cardnum=跟牌数
//            toClient.setCardNum(ClientCards.size());
//            toClient.setActiveCards(null);

//        }else if(actionType==Flag.ACTION_PASS){
//            toClient.setTurn_to_player(gameState.players.indexOf(gameState.currentPlayer));
//            toClient.setPoke_to_player(-1);
//            //no poke to player(include poke quit state)
//            toClient.setAction(actionType);
//            toClient.setStep(gameState.step);
//            toClient.setActiveCards(null);
//        }else if(actionType==Flag.ACTION_RECLAIM){
//            toClient=new ClientStateChange.Builder()
//                    .step(gameState.step)
//                    .action(Flag.ACTION_RECLAIM)
//                    .cardNum(ClientCards.size())
//                    .pokeToPlayer(reclaim)
//                    .turnToPlayer(gameState.players.indexOf(gameState.currentPlayer))
//                    .build();
//        }
//        else{
//            //Flag.ACTION_CHALLENGE

//            toClient.setTurn_to_player(gameState.players.indexOf(gameState.currentPlayer));

//            if(RulesReturn.getPoke_to_player()==Flag.CHALLENGER){
//                toClient.setPoke_to_player(gameState.players.indexOf(copyCurrentPlayer));
//            }
//            else{
//                toClient.setPoke_to_player(gameState.players.indexOf(copyLastPlayer));
//            }

//            toClient.setAction(actionType);
//            toClient.setStep(gameState.step);
//            List<Card>activeCards=new ArrayList<>(gameState.stackCards);

//            activeCards.addAll(gameState.topCards);
//            toClient.setActiveCards(activeCards);
//            toClient.setCardNum(toClient.getActiveCards().size());

//        }System.out.println(6);
//        System.out.printf("%d %d~\n",toClient.turn_to_player,toClient.poke_to_player);
//        System.out.println(RulesReturn);System.out.println(gameState);
//        gameState.changes.add(toClient);

//    }
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
            gameState.currentPlayer=gameState.players.get((gameState.currentPlayer.position+1)%gameState.roomConfig.numberPlayer);
            gameState.lastPlayer=null;
            gameState.stackCards.clear();
            gameState.topCards.addAll(ClientCards);
            gameState.claim=reclaim;
            gameState.step++;

            toClient.setStep(gameState.step);
            toClient.setTurn_to_player(gameState.currentPlayer.position);
            toClient.setPoke_to_player(reclaim);
            toClient.setActiveCards(null);
            toClient.setAction(actionType);
            toClient.setRp(gameState.players);
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
                toClient.setPoke_to_player(-1);
                toClient.setActiveCards(null);
                toClient.setAction(actionType);
                toClient.setRp(gameState.players);

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
                toClient.setRp(gameState.players);
                toClient.setCardNum(0);

                gameState.changes.add(toClient);
            }
        }
        else if(actionType==Flag.ACTION_FOLLOW){
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
            toClient.setRp(gameState.players);

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
            toClient.setRp(gameState.players);

            gameState.stackCards.clear();
            gameState.topCards.clear();

            gameState.changes.add(toClient);
        }

        /**
         *                    _ooOoo_
         *                   o8888888o
         *                   88" . "88
         *                   (| -_- |)
         *                    O\ = /O
         *                ____/`---'\____
         *              .   ' \\| |// `.
         *               / \\||| : |||// \
         *             / _||||| -:- |||||- \
         *               | | \\\ - /// | |
         *             | \_| ''\---/'' | |
         *              \ .-\__ `-` ___/-. /
         *           ___`. .' /--.--\ `. . __
         *        ."" '< `.___\_<|>_/___.' >'"".
         *       | | : `- \`.;`\ _ /`;.`/ - ` : | |
         *         \ \ `-. \_ __\ /__ _/ .-` / /
         * ======`-.____`-.___\_____/___.-`____.-'======
         *                    `=---='
         *
         * .............................................
         *          佛祖保佑             永无BUG
         */
    }

    private void endGame(int winner){
        /*
        *
        * endGame状态传参说明：
        * 传出的ClientStateChange中step更新，rp赋值，turn_to_player及poke_to_player及action赋值Flag.GAME_END
        * 其余均不用管
        *
         */
        gameState.step++;
        ClientStateChange toClient = new ClientStateChange();

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
