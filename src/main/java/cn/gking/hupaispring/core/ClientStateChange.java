package cn.gking.hupaispring.core;

import java.util.ArrayList;
import java.util.List;

public class ClientStateChange {
    int turn_to_player;
    int poke_to_player;;
    List<Card> activeCards;
    //一切牌类变动（揭牌展示揭牌，取牌展示取牌）
    List<Player> rp;
    //当前players列表
    int action;
    int cardNum;
    int step;
    //计时器step

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

    public List<Card> getActiveCards() {
        return activeCards;
    }

    public void setActiveCards(List<Card> activeCards) {
        this.activeCards = activeCards;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public List<Player> getRp() {
        return rp;
    }


    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return Util.toJson(this);
    }

    public void setRp(List<Player> rp) {
        this.rp = rp;
    }

    public static class Builder {
        private final ClientStateChange instance = new ClientStateChange();

        public Builder turnToPlayer(int turnToPlayer) {
            instance.turn_to_player = turnToPlayer;
            return this;
        }

        public Builder pokeToPlayer(int pokeToPlayer) {
            instance.poke_to_player = pokeToPlayer;
            return this;
        }

        public Builder activeCards(List<Card> activeCards) {
            instance.activeCards = new ArrayList<>(activeCards); // 防御性拷贝
            return this;
        }

        public Builder addActiveCard(Card card) {
            if (instance.activeCards == null) {
                instance.activeCards = new ArrayList<>();
            }
            instance.activeCards.add(card);
            return this;
        }

        public Builder action(int action) {
            instance.action = action;
            return this;
        }

        public Builder cardNum(int cardNum) {
            instance.cardNum = cardNum;
            return this;
        }

        public Builder step(int step) {
            instance.step = step;
            return this;
        }

        public ClientStateChange build() {
            // 可在此处添加验证逻辑
            return instance;
        }
        public Builder setActiveCards(List<Card> cards){
            instance.activeCards=cards;
            return this;
        }
    }
}
