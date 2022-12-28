package util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import vo.AGV;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
* 车间模型
* 1. 读取 工件 信息 （）
* 2. 读取 路径 信息 （）
* 3. 读取 AGV 信息 （）*/
@Data
public class WorkStation {
    private  List<WorkPiece> workPieceList=new ArrayList<>();
    private  List<Machine> machinesList=new ArrayList<>();
    private  List<AGV> agvList=new ArrayList<>();
    private  List<Route> routeList=new ArrayList<>();


    public  List<WorkPiece> getWorkPieceList() {
        return workPieceList;
    }

    public  void setWorkPieceList(List<WorkPiece> workPieceList) {
        this.workPieceList = workPieceList;
    }

    public List<Machine> getMachinesList() {
        return machinesList;
    }

    public void setMachinesList(List<Machine> machinesList) {
        this.machinesList = machinesList;
    }

    public static WorkStation load(JSONObject jsonObject){
        WorkStation workStation=new WorkStation();
        JSONArray workPieceArray =jsonObject.getJSONArray("工件集合");
        for (Object name :
                workPieceArray) {
            //System.out.println(name);
            workStation.workPieceList.add(new WorkPiece((String) name));
        }
        System.out.println(workStation.workPieceList);
        //System.out.println("===================");
        JSONArray machineArray=jsonObject.getJSONArray("机器集合");
        for (Object name :
                machineArray) {
            //System.out.println(name);
            workStation.machinesList.add(new Machine((String) name));
        }
        System.out.println( workStation.machinesList);
        System.out.println("===================");
        JSONArray workProcessArray =jsonObject.getJSONArray("工件工序");
        for (int i = 0; i < workProcessArray.size(); i++) {
            //System.out.println(workProcessArray.getJSONArray(i));
            JSONArray process1=workProcessArray.getJSONArray(i);
            for (Object name:
                    process1) {
//                System.out.println(name);
                Process process=new Process((String) name);
                workStation.workPieceList.get(i).addProcess(process);
               // System.out.println(workStation.workPieceList.get(i).getProcessList().get(0).getName());
            }
            //System.out.println(workStation.workPieceList.get(i).getProcessList());
        }
        System.out.println(workStation.workPieceList);
        //机器和工序绑定

        for (int i = 0; i < workStation.workPieceList.size(); i++) {
            for (int k = 0; k < workStation.workPieceList.get(i).getProcessList().size(); k++) {
               String name=workStation.workPieceList.get(i).getProcessList().get(k).getName();
                System.out.println(workStation.workPieceList.get(i).getProcessList().get(k).getName());
                //System.out.println(jsonObject.getJSONArray(name).getJSONObject(0).get("机器")+"------------"+jsonObject.getJSONArray(name).getJSONObject(0).get("时长"));
                JSONArray MOE=jsonObject.getJSONArray(name);
                for (int e = 0; e < MOE.size(); e++) {
                    //System.out.println(MOE.getJSONArray(0).getString(0));
                    System.out.println("=="+jsonObject.getJSONArray(name).getJSONObject(e).get("机器")+jsonObject.getJSONArray(name).getJSONObject(e).get("时长")+"==");
                   String a= (String) MOE.getJSONObject(e).get("机器");
                    int b= Integer.parseInt(MOE.getJSONObject(e).get("时长").toString());
                    System.out.println(b);
////                    workStation.workPieceList.get(i).getProcessList().get(k).getMachineList().put(a,b);
                    workStation.workPieceList.get(i).getProcessList().get(k).getMachineList().put(a,b);
                }
                //初始化默认 工序的默认机器为第一个。
                Random random=new Random();
                String Z="M0";
                int temp=random.nextInt(4)+1;
                Z=Z+temp;
                System.out.println(Z);
                workStation.workPieceList.get(i).getProcessList().get(k).setS_machine(Z);
            }
        }
        System.out.println(workStation.workPieceList.get(3).getProcessList().get(2).getMachineList());
        for (String KEY :
                workStation.workPieceList.get(3).getProcessList().get(2).getMachineList().keySet()) {
            System.out.println(KEY+":"+workStation.workPieceList.get(3).getProcessList().get(2).getMachineList().get(KEY));
        }
        JSONArray AGV_asd=jsonObject.getJSONArray("AGV集合");
        for (Object name :
                AGV_asd) {
            System.out.println(name);
            workStation.agvList.add(new AGV((String) name));
        }
        System.out.println(workStation.workPieceList);
        System.out.println("===========================");
        for (int i = 0; i < workStation.workPieceList.size(); i++) {
            for (int j = 0; j < workStation.workPieceList.get(i).getProcessList().size(); j++) {
                workStation.workPieceList.get(i).getProcessList().get(j).set_machine();
            }
        }
        JSONArray lo=jsonObject.getJSONArray("机器的位置");
        int i=0;
        for (Object o: lo) {
            String a= (String) o;
            int b=Integer.parseInt(a);
            workStation.machinesList.get(i++).setLocation(b);
        }
//        System.out.println(workStation.agvList.get(0));
        System.out.println(workStation.workPieceList);
        System.out.println(workStation.workPieceList.size());
        System.out.println(workStation.workPieceList.get(0).getProcessList().size());
        return  workStation;
    }
}
