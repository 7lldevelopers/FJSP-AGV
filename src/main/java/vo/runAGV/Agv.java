package vo.runAGV;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import util.Machine;
import vo.AGV;

import java.util.List;

//并发  解决路径和机器冲突
public class Agv implements Runnable{
    private Lock lock=new ReentrantLock();
    //共同资源属性 Machine
    private List<Machine> machine;
    //每个AGV自身的任务
    private List<Machine> machineList;
    //自身的执行时间
    private int time;
    //设置AGV的时间
    private AGV agv;
    private int c;
    @Override
    public void run() {
        for (int i = 0; i < machineList.size(); i++) {
            for (int j = 0; j < machine.size(); j++) {
                System.out.println();
                if (machineList.get(i).getName().equals(machine.get(j).getName()))
                    //前置资源 后置为任务
                    time=comTime(machine.get(j),machineList.get(i),time);
            }

        }
        agv.setTime(time);
        c=1;
    }
    //前置资源 后置为任务
    public synchronized int comTime(Machine machine,Machine machine1,int time){
            int time_asd=time;
            if (machine.getTime()<=time_asd) {
                machine.setTime(machine1.getTime()+time_asd);
                return time_asd+machine1.getTime();
            }else {
                int time_zxc=machine.getTime();
                machine.setTime(time_zxc+machine1.getTime());
                return time_zxc+machine1.getTime();
            }
    }
    //深拷贝任务集合  浅拷贝资源机器
    public Agv(List<Machine> mac, List<Machine> machineList, AGV agv) {
        List<Machine> MachineList1=machineList.stream().collect(Collectors.toList());
        this.machine=mac;
        this.machineList=MachineList1;
        this.agv=agv;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
