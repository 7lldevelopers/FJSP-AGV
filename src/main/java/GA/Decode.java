package GA;

import QEA.Chromos;
import util.Machine;
import util.Route;
import util.WorkPiece;
import util.WorkStation;
import vo.AGV;
import vo.runAGV.Agv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//计算车间内AGV制造时间选取最大为time
public class Decode {
    public static void code(WorkStation workStation,Chromosome chromosome ){
        /*
        * @方法1 通过并法编程来实现 计算
        * 计算并发AGV的执行时间time
        * 通过并发解决冲突
        * 工作中的机器  初始化整个机器  判断是否正在工作(用深拷贝 防止出现并发错误)
        * */
//        List<Machine> machineList=new ArrayList<>();
//        for (int i = 0; i < workStation.getMachinesList().size(); i++) {
//            machineList.add(new Machine(workStation.getMachinesList().get(i).getName()));
//            machineList.get(i).setLocation(workStation.getMachinesList().get(i).getLocation());
//            machineList.get(i).setTime(0);
//            machineList.get(i).getAbility().clear();
//        }
//        //清空原有计算结果
//        for (int i = 0; i < chromosome.getDNA().size(); i++) {
//            chromosome.getDNA().get(i).setTime(0);
//        }
//        List<Integer> lastTime=new ArrayList<>();
//        lastTime.clear();
//        //计算在机器加工的时间
//        //记录所有AGV在移动的目标集合mec
//        List<List<Machine>> mec=new ArrayList<>();
//        List<Integer> routeList=new ArrayList<>();
//        for (int i = 0; i < chromosome.getDNA().size(); i++) {
//            int times=0;
//            //设置AGV需要走的机器集合
//            List<Machine> machines=new ArrayList<>();
//             mec.add(machines);
//            if (chromosome.getDNA().get(i).getJopList().size()==0){
//                times=0;
//                chromosome.getDNA().get(i).setTime(0);
//                lastTime.add(0);
//            }else {
//                for (int j = 0; j < chromosome.getDNA().get(i).getJopList().size(); j++) {
//                    for (int k = 0; k < chromosome.getDNA().get(i).getJopList().get(j).getProcessList().size(); k++) {
//                        //times =times+ chromosome.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine().getTime();
//                        mec.get(i).add(chromosome.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine());
//                    }
//                }
//                chromosome.getDNA().get(i).setTime(times);
//                lastTime.add(times);
//            }
//        }
//        //开始并发
//        Agv a1=new Agv(machineList, mec.get(0), chromosome.getDNA().get(0));
//        Agv a2=new Agv(machineList, mec.get(1), chromosome.getDNA().get(1));
//        Agv a3=new Agv(machineList, mec.get(2), chromosome.getDNA().get(2));
//        Thread b1=new Thread(a1);
//        Thread b2=new Thread(a2);
//        Thread b3=new Thread(a3);
//        b1.start();
//        b2.start();
//        b3.start();;
//        try {
//            b1.join();
//            b2.join();
//            b3.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        int max=chromosome.getDNA().get(0).getTime();
//        for (int i = 1; i < chromosome.getDNA().size(); i++) {
//            if (max<=chromosome.getDNA().get(i).getTime())
//            {
//                max=chromosome.getDNA().get(i).getTime();
//            }
//        }
//        chromosome.setTime(max);

        /*
         * @方法2 通过实际的逻辑来计算AGV的并发
         * 通过循环 逻辑解决 冲突
         * */
        List<Machine> machineList=new ArrayList<>();
        for (int i = 0; i < workStation.getMachinesList().size(); i++) {
            machineList.add(new Machine(workStation.getMachinesList().get(i).getName()));
            machineList.get(i).setLocation(workStation.getMachinesList().get(i).getLocation());
            machineList.get(i).setTime(0);
            machineList.get(i).getAbility().clear();
        }
        List<Integer> lastTime=new ArrayList<>();
        //
        List<List<Machine>> mec=new ArrayList<>();
        mec.clear();
        List<Integer> roList=new ArrayList<>();
        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            int times=0;
            List<Machine> machines=new ArrayList<>();
             mec.add(machines);
            if (chromosome.getDNA().get(i).getJopList().size()==0){
                times=0;
                chromosome.getDNA().get(i).setTime(0);
                lastTime.add(0);
            }else {
                for (int j = 0; j < chromosome.getDNA().get(i).getJopList().size(); j++) {
                    for (int k = 0; k < chromosome.getDNA().get(i).getJopList().get(j).getProcessList().size(); k++) {
                        times =times+ chromosome.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine().getTime();
                        mec.get(i).add(chromosome.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine());
                    }
                }
                chromosome.getDNA().get(i).setTime(times);
                lastTime.add(times);
            }
            }
        //并发
        int v=mec.get(0).size();
        for (List<Machine> machines : mec) {
            if(v<=machines.size())
                v=machines.size();
        }
        for (int i = 0; i < mec.size(); i++) {
            if (mec.get(i).size()!=v){
                int r=v-mec.get(i).size();
                for (int j = 0; j < r; j++) {
                    String a="a";
                    mec.get(i).add(new Machine(a));
                }

            }
        }
        boolean set=true;
        int[]  a=new int[3];
        int e=0;
        while(set){
            for (int i = 0; i <mec.size() ; i++) {
                int time_asd=a[i];
                for (int j = 0; j < machineList.size(); j++) {
                    if (machineList.get(j).getName().equals(mec.get(i).get(e).getName())) {
                        if (machineList.get(j).getTime()<=time_asd) {
                            int q=mec.get(i).get(e).getTime();
                            machineList.get(j).setTime(q+time_asd);
                            a[i]=a[i]+q;
                        }else {
                            int p=machineList.get(j).getTime();
                            a[i]=machineList.get(j).getTime()+mec.get(i).get(e).getTime();
                            machineList.get(j).setTime(a[i]);

                        }
                    }
                }
            }
            e=e+1;
            if (v==e)
                set=false;
        }
        int times = 0;
        for (int i = 0; i < 3; i++) {
            if(times<=a[i])
                times=a[i];
        }
        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            chromosome.getDNA().get(i).setTime(a[i]);
        }
        chromosome.setTime(times);
        //比较选取最大的为执行时间time
//        int agv=0;
//        int asd=0;
//        chromosome.setTime(0);
//        for (int i = 0; i < lastTime.size(); i++) {
//            if (asd<lastTime.get(i)){
//                asd=lastTime.get(i);
//                agv=i;
//            }
//        }
//        chromosome.setTime(asd);

    }
    public static void code(WorkStation workStation,Chromos chromosome ){

        /*
         * @方法2 通过实际的逻辑来计算AGV的并发
         * 通过循环 逻辑解决 冲突
         * */
        List<Machine> machineList=new ArrayList<>();
        for (int i = 0; i < workStation.getMachinesList().size(); i++) {
            machineList.add(new Machine(workStation.getMachinesList().get(i).getName()));
            machineList.get(i).setLocation(workStation.getMachinesList().get(i).getLocation());
            machineList.get(i).setTime(0);
            machineList.get(i).getAbility().clear();
        }
        List<Integer> lastTime=new ArrayList<>();
        //
        List<List<Machine>> mec=new ArrayList<>();
        mec.clear();
        List<Integer> roList=new ArrayList<>();
        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            int times=0;
            List<Machine> machines=new ArrayList<>();
            mec.add(machines);
            if (chromosome.getDNA().get(i).getJopList().size()==0){
                times=0;
                chromosome.getDNA().get(i).setTime(0);
                lastTime.add(0);
            }else {
                for (int j = 0; j < chromosome.getDNA().get(i).getJopList().size(); j++) {
                    for (int k = 0; k < chromosome.getDNA().get(i).getJopList().get(j).getProcessList().size(); k++) {
                        times =times+ chromosome.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine().getTime();
                        mec.get(i).add(chromosome.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine());
                    }
                }
                chromosome.getDNA().get(i).setTime(times);
                lastTime.add(times);
            }
        }
        //并发
        int v=mec.get(0).size();
        for (List<Machine> machines : mec) {
            if(v<=machines.size())
                v=machines.size();
        }
        for (int i = 0; i < mec.size(); i++) {
            if (mec.get(i).size()!=v){
                int r=v-mec.get(i).size();
                for (int j = 0; j < r; j++) {
                    String a="a";
                    mec.get(i).add(new Machine(a));
                }

            }
        }
        boolean set=true;
        int[]  a=new int[3];
        int e=0;
        while(set){
            for (int i = 0; i <mec.size() ; i++) {
                int time_asd=a[i];
                for (int j = 0; j < machineList.size(); j++) {
                    if (machineList.get(j).getName().equals(mec.get(i).get(e).getName())) {
                        if (machineList.get(j).getTime()<=time_asd) {
                            int q=mec.get(i).get(e).getTime();
                            machineList.get(j).setTime(q+time_asd);
                            a[i]=a[i]+q;
                        }else {
                            int p=machineList.get(j).getTime();
                            a[i]=machineList.get(j).getTime()+mec.get(i).get(e).getTime();
                            machineList.get(j).setTime(a[i]);

                        }
                    }
                }
            }
            e=e+1;
            if (v==e)
                set=false;
        }
        int times = 0;
        for (int i = 0; i < 3; i++) {
            if(times<=a[i])
                times=a[i];
        }
        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            chromosome.getDNA().get(i).setTime(a[i]);
        }
        chromosome.setTime(times);
        //比较选取最大的为执行时间time
    }
    public Integer DecodeQ_bit(List<Integer> as,WorkStation workStation){
        Chromos chromosome1=new Chromos();
        chromosome1.DNA.clear();
        List<WorkPiece> a=workStation.getWorkPieceList();
//       System.out.println(a.size());
//        System.out.println(a);
//        Collections.shuffle(a);
//        System.out.println(a);
//        System.out.println(a.size());
        chromosome1.DNA=new ArrayList<>();
        chromosome1.DNA.add(new AGV("AGV1"));
        chromosome1.DNA.add(new AGV("AGV2"));
        chromosome1.DNA.add(new AGV("AGV3"));
        chromosome1.setTime(0);
        /*
        * @ i 0 - 7   0-7
        * @ j 8 - 15  0-2
        * @ tes 16 - 42
        * */
        int s=8;
        for (int j = 0; j < 8; j++) {
                chromosome1.DNA.get(as.get(s++)).getJopList().add(a.get(j));
        }
        int tes=16;
        for (int i = 0; i <chromosome1.DNA.size() ; i++) {
            for (int j = 0; j <chromosome1.DNA.get(i).getJopList().size() ; j++) {
                for (int k = 0; k <chromosome1.DNA.get(i).getJopList().get(j).getProcessList().size() ; k++) {
                    chromosome1.DNA.get(i).getJopList().get(j).getProcessList().get(k).set_machine(as.get(tes++));
                }
            }
        }
        //计算当前时间  解码
        chromosome1.setTime(0);
        Decode.code(workStation,chromosome1);

        return chromosome1.getTime();
    }
}
