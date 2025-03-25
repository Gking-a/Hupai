package cn.gking.hupaispring.core;

import java.util.ArrayList;
import java.util.List;

public class Player {
    int id;
    int team=-1;
    String name;
    List<Card> cards=new ArrayList<>();
    NetworkHandler handler;
}
