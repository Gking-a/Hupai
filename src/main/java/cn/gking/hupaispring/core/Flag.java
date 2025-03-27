package cn.gking.hupaispring.core;

public class Flag {
    public static int DEFAULT_NO_CHANGE=0;

    public static int NEXT_PLAYER=1;
    public static int CHALLENGER=2;
    public static int LAST_POKER=3;
    public static int QUIT=4;

    public static int STATE_WAITING_CONNECTION=1;
    public static int STATE_IDLE=2;
    public static int STATE_ASKING=3;
    public static int STAGE_NULL=4;

    public static int ACTION_PASS=1;
    public static int ACTION_FOLLOW=2;
    public static int ACTION_CHALLENGE=3;

    public static int CARD_JOKER=5;

    public static int REGISTER_PLAYER=10;

    public static int GAME_LOSE=7;

    public static int GAME_WIN=14;
//    public static int CARD_POST=2;
//    public static int CARD_QUIT=3;
}
