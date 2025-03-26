package cn.gking.hupaispring.core;

public class AbstractStateChange {
    int turn_to_player;
    int poke_to_player;
    int action;

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

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
