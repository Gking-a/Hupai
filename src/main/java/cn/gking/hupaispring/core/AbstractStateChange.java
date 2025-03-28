package cn.gking.hupaispring.core;

public class AbstractStateChange {
    //下一个出牌
    int turn_to_player;
    //牌给谁(default用于保留)
    int poke_to_player;

    AbstractStateChange(){}
    AbstractStateChange(int x,int y){this.turn_to_player = x;this.poke_to_player = y;}

    public int getTurn_to_player() {
        return turn_to_player;
    }

    public void setTurn_to_player(int turn_to_player) {
        this.turn_to_player = turn_to_player;
    }

    public int getPoke_to_player() {
        return poke_to_player;
    }

    public void setPoke_to_player(int poke_to_player) {
        this.poke_to_player = poke_to_player;
    }
}
