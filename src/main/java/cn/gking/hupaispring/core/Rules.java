package cn.gking.hupaispring.core;

//@wyx 此类全为static
//@wyx 此类判断牌局结束时，action返回的turn to player使用DEFAULT_NO_CHANGE值
//@wyx action中，follow,challenge,pass均触发判断是否胜利
public class Rules {
    static AbstractStateChange action(State gameState, int action){
        return null;
    }
}
