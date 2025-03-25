package cn.gking.hupaispring.core;

public class RoomConfig {
    int numberPoke;
    int numberJoker;
    int numberQuit;
    int numberPlayer;

    public RoomConfig(int numberPoke, int numberJoker, int numberQuit, int numberPlayer) {
        this.numberPoke = numberPoke;
        this.numberJoker = numberJoker;
        this.numberQuit = numberQuit;
        this.numberPlayer = numberPlayer;
        if(numberQuit<0)this.numberQuit=(13*numberPoke+numberJoker)%numberPlayer;
    }
}
