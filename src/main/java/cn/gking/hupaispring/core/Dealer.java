package cn.gking.hupaispring.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.shuffle;

//发牌器
public class Dealer {
    int poke,king,player,discard;

    public Dealer(int poke, int king, int player, int discard) {
        this.poke = poke;
        this.king = king;
        this.player = player;
        this.discard = discard;
        if(discard==Flag.DEFAULT_NO_CHANGE){
            this.discard=(poke*52+king)%player;
        }
    }
    public void refresh(){}
    public void create(){}
    public List<List<Card>> getFinalPoke(){
        List<Card> cards=new ArrayList<>();
        for (int i = 1; i <= player; ++i)
            for (int j = 1; j <= 13; ++j)
                for (int k = 1; k <= 4; ++k) cards.add(new Card(j,k));
        shuffle(cards, new Random());
        ArrayList<List<Card>> objects = new ArrayList<>();

        int id = 0;
        for (int i = 0; i < player; i++) {
            int x = (poke * 52 + king - discard) / player;
            List<Card> now = new ArrayList<>();
            while (x != 0){
                now.add(cards.get(id++));
                x--;
            }
            objects.add(now);
        }
        return objects;
    }
}
