package util;

import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.Data;

import java.util.*;

/*
* 工序
* */
@Data
public class Process {
    //工序名称
    private String name;
    //工序的开始时间
    private int start;
    //工序的加工时间
    private int time;
    //当前工序的选取加工机器
    private Machine machine;
    private String S_machine;
    //所属工件
    private WorkPiece workPiece;
    //工序能个机器集合
    public Map<String,Integer> machineList=new HashMap<>();
    public List<Machine> processMachineList=new ArrayList<>();
    //通过Map<String,Integer> machineLis来得到真个机器列表
    public void setProcessMachineList( Map<String,Integer> machineLis){
        Iterator<String> iterator=machineLis.keySet().iterator();
        int i=0;
        while (iterator.hasNext()){
            String name=iterator.next();
           processMachineList.add(new Machine(name));
            processMachineList.get(i++).setTime(machineLis.get(name));
        }
    }
    //随机分配工序机器
    public void set_machine(){
      setProcessMachineList(machineList);
        int start=(int)(Math.random()*(4));
//        machine=processMachineList.get(temp);
        Machine machine=new Machine(processMachineList.get(start).getName());
        machine.setTime(processMachineList.get(start).getTime());
        this.machine=machine;
    }
    //分配工序的机器
    public void set_machine(int i){
        int start=i;
//        machine=processMachineList.get(temp);
        Machine machine=new Machine(processMachineList.get(start).getName());
        machine.setTime(processMachineList.get(start).getTime());
        this.machine=machine;
    }
    public Map<String, Integer> getMachineList() {
        return machineList;
    }
    public void add(String A,int B){
        machineList.put(A,B);
    }
    public void setMachineList(Map<String, Integer> machineList) {
        this.machineList = machineList;
    }

    public Process(String name){this.name=name;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public WorkPiece getWorkPiece() {
        return workPiece;
    }

    public void setWorkPiece(WorkPiece workPiece) {
        this.workPiece = workPiece;
    }
    public String getS_machine() {
        return S_machine;
    }

    public void setS_machine(String s_machine) {
        S_machine = s_machine;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", start=" + start +
                ", time=" + time +
                ", machine=" + machine +
                ", S_machine='" + S_machine + '\'' +
                ", workPiece=" + workPiece +
                ", machineList=" + machineList +
                ", processMachineList=" + processMachineList +
                '}';
    }

    public List<Machine> getProcessMachineList() {
        return processMachineList;
    }

    public void setProcessMachineList(List<Machine> processMachineList) {
        this.processMachineList = processMachineList;
    }

}
