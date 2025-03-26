package cn.gking.hupaispring.core;

public class NetworkProtocol {
    int step;
    int room_init;
    ClientStateChange clientStateChange;

    // 为所有成员编写链式调用方法
    public NetworkProtocol step(int step) {
        this.step = step;
        return this;
    }

    public NetworkProtocol room_init(int room_init) {
        this.room_init = room_init;
        return this;
    }
}
