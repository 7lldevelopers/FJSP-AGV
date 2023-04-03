package util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/*
* 机器
* */
@Data
public class Machine {
    //机器名称
    private String name;
    //机器能干的工序
    private List<Integer> ability=new ArrayList<>();
    //机器的当前位置
    private int location;
    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public void addTime(int time1){
        time=time+time1;
    }
    public void subTime(int time2){
        time=time+time2;
    }
    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public Machine(String name){ this.name=name;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAbility() {
        return ability;
    }

    public void setAbility(List<Integer> ability) {
        this.ability = ability;
    }
}
