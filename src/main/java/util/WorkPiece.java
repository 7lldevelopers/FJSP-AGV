package util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
* 工件
* */
@Data
public class WorkPiece {
    //工序名称
    private String name;
    //工序列表
    private  List<Process> processList=new ArrayList<>();
    //默认工序当前的选择机器的列表
    private  List<Machine> machineList=new ArrayList<>();

    public List<Machine> getMachineList() {
        return machineList;
    }

    //上一道工序的完成时间+AGV行驶路径的时间
    private  int LastTime;
    public void addProcess(Process pro){
        processList.add(pro);
    }
    public void setMachineList(List<Machine> machineList) {
        this.machineList = machineList;
    }

    public WorkPiece(String name){this.name=name;}
    public int getLastTime() {
        return LastTime;
    }

    public void setLastTime(int lastTime) {
        LastTime = lastTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Process> getProcessList() {
        return processList;
    }

    public void setProcessList(List<Process> processList) {
        this.processList = processList;
    }
}
