package cn.gking.hupaispring.core;

import java.util.List;
//发牌器
public class Dealer {
    int poke,king,player,quit;

    public Dealer(int poke, int king, int player, int quit) {
        this.poke = poke;
        this.king = king;
        this.player = player;
        this.quit = quit;
        if(quit==Flag.DEFAULT_NO_CHANGE){
            this.quit=(poke*13+king)%player;
        }
    }
    public void refresh(){}
    public void create(){}
    public List<List<Card>> getFinalPoke(){}
}
