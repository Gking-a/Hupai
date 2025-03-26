package cn.gking.hupaispring.core;

public class NetworkProtocol {
    int step=0;
    int room_init;
    ClientStateChange clientStateChange=null;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public ClientStateChange getClientStateChange() {
        return clientStateChange;
    }

    public void setClientStateChange(ClientStateChange clientStateChange) {
        this.clientStateChange = clientStateChange;
    }

    public int getRoom_init() {
        return room_init;
    }

    public void setRoom_init(int room_init) {
        this.room_init = room_init;
    }

    // 为所有成员编写链式调用方法
    public NetworkProtocol step(int step) {
        this.step = step;
        return this;
    }

    public NetworkProtocol room_init(int room_init) {
        this.room_init = room_init;
        return this;
    }

    @Override
    public String toString() {
        return Util.toJson(this);
    }
}
