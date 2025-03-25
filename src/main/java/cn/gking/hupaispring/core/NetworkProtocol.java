package cn.gking.hupaispring.core;

public class NetworkProtocol {
    int step;
    int room_init;
    int player_turn_to;
    int card_post;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getRoom_init() {
        return room_init;
    }

    public void setRoom_init(int room_init) {
        this.room_init = room_init;
    }

    public int getPlayer_turn_to() {
        return player_turn_to;
    }

    public void setPlayer_turn_to(int player_turn_to) {
        this.player_turn_to = player_turn_to;
    }

    public int getCard_post() {
        return card_post;
    }

    public void setCard_post(int card_post) {
        this.card_post = card_post;
    }

    public int getCard_claim() {
        return card_claim;
    }

    public void setCard_claim(int card_claim) {
        this.card_claim = card_claim;
    }

    int card_claim;

    // 为所有成员编写链式调用方法
    public NetworkProtocol step(int step) {
        this.step = step;
        return this;
    }

    public NetworkProtocol room_init(int room_init) {
        this.room_init = room_init;
        return this;
    }

    public NetworkProtocol player_turn_to(int player_turn_to) {
        this.player_turn_to = player_turn_to;
        return this;
    }

    public NetworkProtocol card_post(int card_post) {
        this.card_post = card_post;
        return this;
    }

    public NetworkProtocol card_claim(int card_claim) {
        this.card_claim = card_claim;
        return this;
    }

    @Override
    public String toString() {
        return Util.toJson(this);
    }
}
