package vo;

import lombok.Data;
import util.Machine;
import util.WorkPiece;

import java.util.ArrayList;
import java.util.List;
@Data
public class AGV  {
    //AGVid
    private String name;
    //AGV的执行订单
    private  List<WorkPiece> jopList=new ArrayList<>();
    //AGV的当前订单目标机器集合
    private  List<Integer> machineLocation=new ArrayList<>();
    //当前AGV运输结束的的时间
    private int time;
    //路径搜索的目标
    public void locationSearch(){
        machineLocation.add(0);
        for (WorkPiece workPiece   :
        jopList) {
            for (int i = 0; i < workPiece.getProcessList().size(); i++) {
                machineLocation.add(workPiece.getProcessList().get(i).getMachine().getLocation());
            }
            machineLocation.add(25);
        }
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public AGV(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getMachineLocation() {
        return machineLocation;
    }

    public void setMachineLocation(List<Integer> machineLocation) {
        this.machineLocation = machineLocation;
    }


    public List<WorkPiece> getJopList() {
        return jopList;
    }

    public void setJopList(List<WorkPiece> jopList) {
        this.jopList = jopList;
    }



}
