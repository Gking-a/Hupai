package cn.gking.hupaispring.core;

//@wyx 此类全为static
//@wyx 此类判断牌局结束时，action返回的turn to player使用DEFAULT_NO_CHANGE值
//@wyx action中，follow,challenge,pass均触发判断是否胜利
public class Rules {
    static AbstractStateChange action(State gameState, int action){
        int position_last = gameState.players.indexOf(gameState.lastPlayer);
        int position_current = gameState.players.indexOf(gameState.currentPlayer);
        AbstractStateChange result=new AbstractStateChange();
        if (gameState.currentPlayer.cards.isEmpty())
            return new AbstractStateChange(Flag.DEFAULT_NO_CHANGE,Flag.DEFAULT_NO_CHANGE);
        if (action == Flag.ACTION_PASS) {
            result.turn_to_player = Flag.NEXT_PLAYER;
            if ((position_current + 1) % gameState.roomConfig.numberPlayer == position_last)
                result.poke_to_player = Flag.QUIT;
            else result.poke_to_player = Flag.DEFAULT_NO_CHANGE;
        }
        if (action == Flag.ACTION_CHALLENGE) {
            boolean chSuccess = false;
            for (Card i:gameState.topCards) if (i.rank != gameState.claim && i.suit != Flag.QUIT){
                chSuccess = true;
                break;
            }
            if (chSuccess) result = new AbstractStateChange(Flag.CHALLENGER, Flag.LAST_POKER);
            else result = new AbstractStateChange(Flag.LAST_POKER,Flag.CHALLENGER);
        }
        if (action == Flag.ACTION_FOLLOW) {
            result.poke_to_player = Flag.DEFAULT_NO_CHANGE;
            result.turn_to_player = Flag.NEXT_PLAYER;
        }
        return result;
    }
}
