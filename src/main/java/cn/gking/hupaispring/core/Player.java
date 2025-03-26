package cn.gking.hupaispring.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    int id;
    int team=-1;
    String name;
    List<Card> cards=new ArrayList<>();
    int position;
    public Player(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Player player = (Player) object;
        return Objects.equals(name, player.name);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
