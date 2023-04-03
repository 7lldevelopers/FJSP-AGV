package GA;

import QEA.Chromos;
import map.Maprea;
import org.jfree.chart.util.HexNumberFormat;
import sun.font.FontRunIterator;
import util.Machine;
import util.Route;
import util.WorkPiece;
import util.WorkStation;
import vo.AGV;
import vo.runAGV.Agv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//计算车间内AGV制造时间选取最大为time
public class Decode {
    public static void code_wenti(WorkStation workStation, Chromosome chromosome) {
//        /*
//        * @方法1 通过并法编程来实现 计算
//        * 计算并发AGV的执行时间time
//        * 通过并发解决冲突
//        * 工作中的机器  初始化整个机器  判断是否正在工作(用深拷贝 防止出现并发错误)
//        * */
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
        List<Machine> machineList = new ArrayList<>();
        for (int i = 0; i < workStation.getMachinesList().size(); i++) {
            machineList.add(new Machine(workStation.getMachinesList().get(i).getName()));
            machineList.get(i).setLocation(workStation.getMachinesList().get(i).getLocation());
            machineList.get(i).setTime(0);
            machineList.get(i).getAbility().clear();
        }
        List<Integer> lastTime = new ArrayList<>();
        //这里是存储AGV访问机器的集合
        List<List<Machine>> mec = new ArrayList<>();
        mec.clear();
        List<Integer> roList = new ArrayList<>();
        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            int times = 0;
            List<Machine> machines = new ArrayList<>();
            mec.add(machines);
            if (chromosome.getDNA().get(i).getJopList().size() == 0) {
                times = 0;
                chromosome.getDNA().get(i).setTime(0);
                lastTime.add(0);
            } else {
                for (int j = 0; j < chromosome.getDNA().get(i).getJopList().size(); j++) {
                    for (int k = 0; k < chromosome.getDNA().get(i).getJopList().get(j).getProcessList().size(); k++) {
                        times = times + chromosome.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine().getTime();
                        mec.get(i).add(chromosome.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine());
                    }
                }
                chromosome.getDNA().get(i).setTime(times);
                lastTime.add(times);
            }
        }
        //并发  存储的机器集合要相同
        int v = mec.get(0).size();
        for (List<Machine> machines : mec) {
            if (v <= machines.size())
                v = machines.size();
        }
        for (int i = 0; i < mec.size(); i++) {
            if (mec.get(i).size() != v) {
                int r = v - mec.get(i).size();
                for (int j = 0; j < r; j++) {
                    String a = "a";
                    mec.get(i).add(new Machine(a));
                }

            }
        }

        //调度数据 存储
        List<Integer> A1 = new ArrayList<>();
        List<Integer> B1 = new ArrayList<>();
        List<Integer> C1 = new ArrayList<>();
        List<Integer> D1 = new ArrayList<>();
        List<String> A = new ArrayList<>();
        A.add("M1 ");
        List<String> B = new ArrayList<>();
        B.add("M2 ");
        List<String> C = new ArrayList<>();
        C.add("M3 ");
        List<String> D = new ArrayList<>();
        D.add("M4 ");
        List<String> E = new ArrayList<>();
        E.add("AGV1 ");
        List<String> F = new ArrayList<>();
        F.add("AGV2 ");
        List<String> G = new ArrayList<>();
        G.add("AGV3 ");
        List<String> H = new ArrayList<>();
        H.add("各AGV分配订单情况: ");
        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            H.add("AGV" + i);
            H.add(" ");
            for (int j = 0; j < chromosome.getDNA().get(i).getJopList().size(); j++) {
                H.add(chromosome.getDNA().get(i).getJopList().get(j).getName());
            }
            H.add(" ");
        }
        List<String> I = new ArrayList<>();
        I.add("冲突发生位置及冲突时间: ");
        List<String> K = new ArrayList<>();
        K.add("机器的利用效率 ");

        //冲突的计算方式
        boolean set = true;
        int[] a = new int[3];
        int e = 0;
        /*
         * 核心思路：
         * 是将当前的4个机器定义 时间戳 a
         * 将遍历3个AGV任务去遍历机器的时间轴 去得到他们的信息
         * */
        while (set) {
            for (int i = 0; i < mec.size(); i++) {
                int time_asd = a[i];
                for (int j = 0; j < machineList.size(); j++) {
                    if (machineList.get(j).getName().equals(mec.get(i).get(e).getName())) {
                        if (machineList.get(j).getTime() <= time_asd) {

                            switch (j) { //存储机器 数据1
                                case 0: {
                                    A.add(String.valueOf(time_asd + " "));
                                    A.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
                                    A1.add(time_asd);
                                    A1.add(time_asd + mec.get(i).get(e).getTime());
                                    break;
                                }
                                case 1: {
                                    B.add(String.valueOf(time_asd + " "));
                                    B.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
                                    B1.add(time_asd);
                                    B1.add(time_asd + mec.get(i).get(e).getTime());
                                    break;
                                }
                                case 2: {
                                    C.add(String.valueOf(time_asd) + " ");
                                    C.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
                                    C1.add(time_asd);
                                    C1.add(time_asd + mec.get(i).get(e).getTime());
                                    break;
                                }
                                case 3: {
                                    D.add(String.valueOf(time_asd + " "));
                                    D.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
                                    D1.add(time_asd);
                                    D1.add(time_asd + mec.get(i).get(e).getTime());
                                    break;
                                }
                            }
                            switch (i) { //存储AGV 数据1
                                case 0: {
                                    E.add(String.valueOf(time_asd + " "));
                                    E.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
                                    break;
                                }
                                case 1: {
                                    F.add(String.valueOf(time_asd + " "));
                                    F.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
                                    break;
                                }
                                case 2: {
                                    G.add(String.valueOf(time_asd + " "));
                                    G.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
                                    break;
                                }
                            }
                            int q = mec.get(i).get(e).getTime();
                            machineList.get(j).setTime(q + time_asd);
                            a[i] = a[i] + q;


                        } else {

                            int p = machineList.get(j).getTime();
                            //ct 为冲突的时刻
                            int ct = p - a[i];
                            I.add("位置: " + mec.get(i).get(e).getName() + "  AGV冲突等待时间: " + ct + "   ");
                            a[i] = machineList.get(j).getTime() + mec.get(i).get(e).getTime();
                            machineList.get(j).setTime(a[i]);

                            //存储机器 数据1
                            switch (j) {
                                case 0: {
                                    A.add(String.valueOf(p + " "));
                                    A.add(String.valueOf(a[i] + " "));
                                    A1.add(p);
                                    A1.add(a[i]);
                                    break;
                                }
                                case 1: {
                                    B.add(String.valueOf(p) + " ");
                                    B.add(String.valueOf(a[i] + " "));
                                    B1.add(p);
                                    B1.add(a[i]);
                                    break;
                                }
                                case 2: {
                                    C.add(String.valueOf(p + " "));
                                    C.add(String.valueOf(a[i] + " "));
                                    C1.add(p);
                                    C1.add(a[i]);
                                    break;
                                }
                                case 3: {
                                    D.add(String.valueOf(p) + " ");
                                    D.add(String.valueOf(a[i] + " "));
                                    D1.add(p);
                                    D1.add(a[i]);
                                    break;
                                }
                            }
                            switch (i) { //存储AGV 数据1
                                case 0: {
                                    E.add(String.valueOf(p + " "));
                                    E.add(String.valueOf(a[i] + " "));
                                    break;
                                }
                                case 1: {
                                    F.add(String.valueOf(p) + " ");
                                    F.add(String.valueOf(a[i] + " "));
                                    break;
                                }
                                case 2: {
                                    G.add(String.valueOf(p + " "));
                                    G.add(String.valueOf(a[i] + " "));
                                    break;
                                }
                            }

                        }
                    }
                }
            }
            e = e + 1;
            if (v == e)
                set = false;
        }

//        try{
//            BufferedWriter bw; bw=new BufferedWriter(new FileWriter("/home/qill/桌面/ssh.txt",true));
//            for (String m :
//                    A) {
//                bw.write(m);
//            }
//            bw.newLine();
//            for (String m :
//                    B) {
//                bw.write(m);
//            }
//            bw.newLine();
//            for (String m :
//                    C) {
//                bw.write(m);
//            }
//            bw.newLine();
//            bw.close();
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        }
//        A.clear();
//        B.clear();
//        C.clear();
        int times = 0;
        for (int i = 0; i < 3; i++) {
            if (times <= a[i])
                times = a[i];
        }
        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            chromosome.getDNA().get(i).setTime(a[i]);
        }
        chromosome.setTime(times);
        //计算机器的利用效率
        int tems = 0;
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 1; i < A1.size(); i = i + 2) {
            tems = tems + A1.get(i) - A1.get(i - 1);
        }
        int asdaga = A1.get(A1.size() - 1);
        float core = (float) tems / asdaga;
        K.add("MO1 " + df.format(core) + " ");
        tems = 0;
        for (int i = 1; i < B1.size(); i = i + 2) {
            tems = tems + B1.get(i) - B1.get(i - 1);
        }
        float core1 = (float) tems / B1.get(B1.size() - 1);
        K.add("MO2 " + df.format(core1) + " ");
        tems = 0;
        for (int i = 1; i < C1.size(); i = i + 2) {
            tems = tems + C1.get(i) - C1.get(i - 1);
        }
        float core2 = (float) tems / C1.get(C1.size() - 1);
        K.add("MO2 " + df.format(core2) + " ");
        tems = 0;
        for (int i = 1; i < D1.size(); i = i + 2) {
            tems = tems + D1.get(i) - D1.get(i - 1);
        }
        float core3 = (float) tems / D1.get(D1.size() - 1);
        K.add("MO2 " + df.format(core3) + " ");
        //输入结果为176的最终结果
//       if(times<176){
        try {
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter("/home/qill/桌面/ssh.txt", true));
            for (String m :
                    A) {
                bw.write(m);
            }
            bw.newLine();
            for (String m :
                    B) {
                bw.write(m);
            }
            bw.newLine();
            for (String m :
                    C) {
                bw.write(m);
            }
            bw.newLine();
            for (String m :
                    D) {
                bw.write(m);
            }
            bw.newLine();
            for (String m :
                    E) {
                bw.write(m);
            }
            bw.newLine();
            for (String m :
                    F) {
                bw.write(m);
            }
            bw.newLine();
            for (String m :
                    G) {
                bw.write(m);
            }
            bw.newLine();
            for (String m :
                    H) {
                bw.write(m);
            }
            bw.newLine();
            for (String m :
                    I) {
                bw.write(m);
            }
            bw.newLine();
            for (String m :
                    K) {
                bw.write(m);
            }
            bw.newLine();
            bw.write("times： =" + times);
            bw.newLine();
            bw.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
//       }
        A.clear();
        B.clear();
        C.clear();
        D.clear();
        E.clear();
        F.clear();
        G.clear();
        H.clear();
        I.clear();
        K.clear();
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

    public static void code(WorkStation workStation, Chromosome chromosome) {
        List<Machine> machineList = new ArrayList<>();
        for (int i = 0; i < workStation.getMachinesList().size(); i++) {
            machineList.add(new Machine(workStation.getMachinesList().get(i).getName()));
            machineList.get(i).setLocation(workStation.getMachinesList().get(i).getLocation());
            machineList.get(i).setTime(0);
            machineList.get(i).getAbility().clear();
        }
        //存储每一个AGV执行的最后时间
        List<Integer> lastTime = new ArrayList<>();
        //这里是存储AGV访问机器的集合
        List<List<Machine>> mec = new ArrayList<>();
        mec.clear();
        //存储路径节点 信息来获取对应的时间戳
        List<List<roNode>> roList = new ArrayList<>();

        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            List<Machine> machines = new ArrayList<>();
            mec.add(machines);
            if (chromosome.getDNA().get(i).getJopList().size() == 0) {
                chromosome.getDNA().get(i).setTime(0);
                lastTime.add(0);
            } else {
                for (int j = 0; j < chromosome.getDNA().get(i).getJopList().size(); j++) {
                    for (int k = 0; k < chromosome.getDNA().get(i).getJopList().get(j).getProcessList().size(); k++) {
                        mec.get(i).add(chromosome.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine());
                    }
                }
            }
        }

        /*
        * 设置所有机器的位置
        * MO1 MO2 MO3 MO4
        *  1  19  31  49
        * */
        for (int i = 0; i < mec.size(); i++) {
            for (int j = 0; j < mec.get(i).size(); j++) {
                switch (mec.get(i).get(j).getName()){
                    case "M01":{
                        mec.get(i).get(j).setLocation(1);
                        break;
                    }
                    case "M02":{
                        mec.get(i).get(j).setLocation(19);
                        break;
                    }
                    case "M03":{
                        mec.get(i).get(j).setLocation(31);
                        break;
                    }
                    case "M04":{
                        mec.get(i).get(j).setLocation(49);
                        break;
                    }
                }
            }
        }
        /*
        * 查找路径节点。
        * */
        List<roNode> twp = null;
        for (int i = 0; i < mec.size(); i++) {
            twp = new ArrayList<>();
            for (int j = 0; j < mec.get(i).size(); j++) {
                twp.add(new roNode(mec.get(i).get(j).getLocation(),mec.get(i).get(j).getTime()));
            }
            roList.add(twp);
        }
        //加入路径  和价值
        for (int i = 0; i < 3; i++) {
            twp=new ArrayList<>();
            Maprea maprea=null;
            List<roNode> ae=null;
            if(roList.get(i).size()>0 && roList.get(i).get(0).getNode()!=1){
                maprea=new Maprea(1,roList.get(i).get(0).getNode());
                ae=new ArrayList<>();
                ae=maprea.getLocst();
                for (int k = 0; k <ae.size(); k++) {
                    twp.add(ae.get(k));
                }
            }
            for (int j = 1; j < roList.get(i).size(); j++) {
                //加入初始路径
                if (roList.get(i).get(j-1).getNode()==roList.get(i).get(j).getNode()){
                    twp.add(roList.get(i).get(j-1));
                }else {
                    twp.add(roList.get(i).get(j-1));
                    maprea=new Maprea(roList.get(i).get(j-1).getNode(),roList.get(i).get(j).getNode());
                    //将路径加入
                    ae=new ArrayList<>();
                    ae=maprea.getLocst();
                    for (int k = 0; k <ae.size(); k++) {
                        twp.add(ae.get(k));
                    }
                }
            }
            if(roList.get(i).size()>0){
                twp.add(roList.get(i).get(roList.get(i).size()-1));
            }
            roList.add(twp);
        }
        //不全位置 后面好 计算
        int v1=roList.get(3).size();
        for (int i = 3; i < roList.size(); i++) {
            if (v1<=roList.get(i).size()){
                v1=roList.get(i).size();
            }
        }
        //并发  存储的机器集合要相同

        for (int i = 3; i < roList.size(); i++) {
            if (roList.get(i).size() != v1) {
                int r = v1 - roList.get(i).size();
                for (int j = 0; j < r; j++) {
                    roList.get(i).add(new roNode(-1,0));
                }
            }
        }

        //调度数据 存储
        List<Integer> A1 = new ArrayList<>();
        List<Integer> B1 = new ArrayList<>();
        List<Integer> C1 = new ArrayList<>();
        List<Integer> D1 = new ArrayList<>();
        List<String> A = new ArrayList<>();
        A.add("M1 ");
        List<String> B = new ArrayList<>();
        B.add("M2 ");
        List<String> C = new ArrayList<>();
        C.add("M3 ");
        List<String> D = new ArrayList<>();
        D.add("M4 ");
        List<String> E = new ArrayList<>();
        E.add("AGV1 ");
        List<String> F = new ArrayList<>();
        F.add("AGV2 ");
        List<String> G = new ArrayList<>();
        G.add("AGV3 ");
        List<String> H = new ArrayList<>();
        H.add("各AGV分配订单情况: ");
        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            H.add("AGV" + i);
            H.add(" ");
            for (int j = 0; j < chromosome.getDNA().get(i).getJopList().size(); j++) {
                H.add(chromosome.getDNA().get(i).getJopList().get(j).getName());
            }
            H.add(" ");
        }
        List<String> I = new ArrayList<>();
        I.add("冲突发生位置及冲突时间: ");
        List<String> K = new ArrayList<>();
        K.add("机器的利用效率 ");

        //冲突的计算方式
        boolean set = true;
        int[] a = new int[3];
        int e = 0;
        //路径初始化
        List<roNode> lujing=new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            lujing.add(new roNode(i,0));
        }
        List<List<String>> lu=new ArrayList<>();
        List<String> mande=null;
        for (int i = 1; i < 50; i++) {
                        mande=new ArrayList<>();
            mande.add("road"+i+" -");
            lu.add(mande);
        }
        boolean set1=true;
        int[] b1=new int[3];
        b1[0]=0;b1[1]=0;b1[2]=0;
        int e1=0;
        while (set1) {
            for (int i = 3; i < roList.size(); i++) {
                //机器时间轴
                int t=i-3;
                int time_asd1 = b1[t];
                for (int j = 0; j < lujing.size(); j++) {
                    if (lujing.get(j).getNode() == roList.get(i).get(e1).getNode()) {
                        if (lujing.get(j).getCast() <= time_asd1) {
                            //xie ru shu ju
                            int r1=lujing.get(j).getNode();
                            lu.get(r1-1).add(" "+time_asd1+", ");
                            int h=time_asd1+roList.get(i).get(e1).getCast();
                            lu.get(r1-1).add(" "+h+", ");
                            int q1 = roList.get(i).get(e1).getCast();
                            lujing.get(j).setCast(q1 + time_asd1);
                            b1[t] = b1[t] + q1;
                        } else {
                            int p1 =  lujing.get(j).getCast();
                            b1[t] =lujing.get(j).getCast() + roList.get(i).get(e1).getCast();
                            lujing.get(j).setCast(b1[t]);
                            lu.get(lujing.get(j).getNode()-1).add(" "+p1+"  ，");
                            lu.get(lujing.get(j).getNode()-1).add(" "+b1[t]+" ，");
                        }
                    }
                }
            }
            e1 = e1 + 1;
            if (v1 == e1) set1 = false;
        }
        /*
         * 核心思路：
         * 是将当前的4个机器定义 时间戳 a
         * 将遍历3个AGV任务去遍历机器的时间轴 去得到他们的信息
         * */
//        while (set) {
//            for (int i = 0; i < mec.size(); i++) {
//                int time_asd = a[i];
//                for (int j = 0; j < machineList.size(); j++) {
//                    if (machineList.get(j).getName().equals(mec.get(i).get(e).getName())) {
//                        if (machineList.get(j).getTime() <= time_asd) {
//                            switch (j) { //存储机器 数据1
//                                case 0: {
//                                    A.add(String.valueOf(time_asd + " "));
//                                    A.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
//                                    A1.add(time_asd);
//                                    A1.add(time_asd + mec.get(i).get(e).getTime());
//                                    break;
//                                }
//                                case 1: {
//                                    B.add(String.valueOf(time_asd + " "));
//                                    B.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
//                                    B1.add(time_asd);
//                                    B1.add(time_asd + mec.get(i).get(e).getTime());
//                                    break;
//                                }
//                                case 2: {
//                                    C.add(String.valueOf(time_asd) + " ");
//                                    C.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
//                                    C1.add(time_asd);
//                                    C1.add(time_asd + mec.get(i).get(e).getTime());
//                                    break;
//                                }
//                                case 3: {
//                                    D.add(String.valueOf(time_asd + " "));
//                                    D.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
//                                    D1.add(time_asd);
//                                    D1.add(time_asd + mec.get(i).get(e).getTime());
//                                    break;
//                                }
//                            }
//                            switch (i) { //存储AGV 数据1
//                                case 0: {
//                                    E.add(String.valueOf(time_asd + " "));
//                                    E.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
//                                    break;
//                                }
//                                case 1: {
//                                    F.add(String.valueOf(time_asd + " "));
//                                    F.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
//                                    break;
//                                }
//                                case 2: {
//                                    G.add(String.valueOf(time_asd + " "));
//                                    G.add(String.valueOf(time_asd + mec.get(i).get(e).getTime() + " "));
//                                    break;
//                                }
//                            }
//                            int q = mec.get(i).get(e).getTime();
//                            machineList.get(j).setTime(q + time_asd);
//                            a[i] = a[i] + q;
//                        } else {
//                            int p = machineList.get(j).getTime();
//                            //ct 为冲突的时刻
//                            int ct = p - a[i];
//                            I.add("位置: " + mec.get(i).get(e).getName() + "  AGV冲突等待时间: " + ct + "   ");
//                            a[i] = machineList.get(j).getTime() + mec.get(i).get(e).getTime();
//                            machineList.get(j).setTime(a[i]);
//                            //存储机器 数据1
//                            switch (j) {
//                                case 0: {
//                                    A.add(String.valueOf(p + " "));
//                                    A.add(String.valueOf(a[i] + " "));
//                                    A1.add(p);
//                                    A1.add(a[i]);
//                                    break;
//                                }
//                                case 1: {
//                                    B.add(String.valueOf(p) + " ");
//                                    B.add(String.valueOf(a[i] + " "));
//                                    B1.add(p);
//                                    B1.add(a[i]);
//                                    break;
//                                }
//                                case 2: {
//                                    C.add(String.valueOf(p + " "));
//                                    C.add(String.valueOf(a[i] + " "));
//                                    C1.add(p);
//                                    C1.add(a[i]);
//                                    break;
//                                }
//                                case 3: {
//                                    D.add(String.valueOf(p) + " ");
//                                    D.add(String.valueOf(a[i] + " "));
//                                    D1.add(p);
//                                    D1.add(a[i]);
//                                    break;
//                                }
//                            }
//                            switch (i) { //存储AGV 数据1
//                                case 0: {
//                                    E.add(String.valueOf(p + " "));
//                                    E.add(String.valueOf(a[i] + " "));
//                                    break;
//                                }
//                                case 1: {
//                                    F.add(String.valueOf(p) + " ");
//                                    F.add(String.valueOf(a[i] + " "));
//                                    break;
//                                }
//                                case 2: {
//                                    G.add(String.valueOf(p + " "));
//                                    G.add(String.valueOf(a[i] + " "));
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            e = e + 1;
//            if (v == e)
//                set = false;
//        }
      //  这里表示路径的时间表：路径也是先来先服务，，让AGV同时走A[1，2，3] 来记录时间。一个是当前机器的时间 一个是当前路径的时间。。。。祝贺。
        int times1=0;
        for (int i = 0; i < 3; i++) {
            if (times1<=b1[i])
                times1=b1[i];
        }

//        int times = 0;
//        for (int i = 0; i < 3; i++) {
//            if (times <= a[i])
//                times = a[i];
//        }
        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            chromosome.getDNA().get(i).setTime(b1[i]);
        }
        chromosome.setTime(times1);
        //计算机器的利用效率
//        int tems = 0;
//        DecimalFormat df = new DecimalFormat("0.00");
//        for (int i = 1; i < A1.size(); i = i + 2) {
//            tems = tems + A1.get(i) - A1.get(i - 1);
//        }
//        int asdaga = A1.get(A1.size() - 1);
//        float core = (float) tems / asdaga;
//        K.add("MO1 " + df.format(core) + " ");
//        tems = 0;
//        for (int i = 1; i < B1.size(); i = i + 2) {
//            tems = tems + B1.get(i) - B1.get(i - 1);
//        }
//        float core1 = (float) tems / B1.get(B1.size() - 1);
//        K.add("MO2 " + df.format(core1) + " ");
//        tems = 0;
//        for (int i = 1; i < C1.size(); i = i + 2) {
//            tems = tems + C1.get(i) - C1.get(i - 1);
//        }
//        float core2 = (float) tems / C1.get(C1.size() - 1);
//        K.add("MO2 " + df.format(core2) + " ");
//        tems = 0;
//        for (int i = 1; i < D1.size(); i = i + 2) {
//            tems = tems + D1.get(i) - D1.get(i - 1);
//        }
//        float core3 = (float) tems / D1.get(D1.size() - 1);
//        K.add("MO2 " + df.format(core3) + " ");
        //输入结果为176的最终结果
//       if(times<176){
        try {
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter("/home/qill/桌面/wanlesssss.txt", true));
            for (List<String> ade:
            lu){
                for (String dea1:
                     ade) {
                    bw.write(dea1);
                }
                bw.newLine();
            }
//            for (String m :
//                    A) {
//                bw.write(m);
//            }
//            bw.newLine();
//            for (String m :
//                    B) {
//                bw.write(m);
//            }
//            bw.newLine();
//            for (String m :
//                    C) {
//                bw.write(m);
//            }
//            bw.newLine();
//            for (String m :
//                    D) {
//                bw.write(m);
//            }
//            bw.newLine();
//            for (String m :
//                    E) {
//                bw.write(m);
//            }
//            bw.newLine();
//            for (String m :
//                    F) {
//                bw.write(m);
//            }
//            bw.newLine();
//            for (String m :
//                    G) {
//                bw.write(m);
//            }
//            bw.newLine();
//            for (String m :
//                    H) {
//                bw.write(m);
//            }
//            bw.newLine();
//            for (String m :
//                    I) {
//                bw.write(m);
//            }
//            bw.newLine();
//            for (String m :
//                    K) {
//                bw.write(m);
//            }
            bw.newLine();
            bw.write("times： =" + times1);
            bw.newLine();
            bw.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
//       }
        A.clear();
        B.clear();
        C.clear();
        D.clear();
        E.clear();
        F.clear();
        G.clear();
        H.clear();
        I.clear();
        K.clear();

        lu.clear();
        roList.clear();
        twp.clear();
        chromosome.setTime(times1);

        System.out.println("***********"+times1+"************");
    }

    public static void code(WorkStation workStation, Chromos chromosome) {

        /*
         * @方法2 通过实际的逻辑来计算AGV的并发
         * 通过循环 逻辑解决 冲突
         * */
        List<Machine> machineList = new ArrayList<>();
        for (int i = 0; i < workStation.getMachinesList().size(); i++) {
            machineList.add(new Machine(workStation.getMachinesList().get(i).getName()));
            machineList.get(i).setLocation(workStation.getMachinesList().get(i).getLocation());
            machineList.get(i).setTime(0);
            machineList.get(i).getAbility().clear();
        }
        List<Integer> lastTime = new ArrayList<>();
        //
        List<List<Machine>> mec = new ArrayList<>();
        mec.clear();
        List<Integer> roList = new ArrayList<>();
        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            int times = 0;
            List<Machine> machines = new ArrayList<>();
            mec.add(machines);
            if (chromosome.getDNA().get(i).getJopList().size() == 0) {
                times = 0;
                chromosome.getDNA().get(i).setTime(0);
                lastTime.add(0);
            } else {
                for (int j = 0; j < chromosome.getDNA().get(i).getJopList().size(); j++) {
                    for (int k = 0; k < chromosome.getDNA().get(i).getJopList().get(j).getProcessList().size(); k++) {
                        times = times + chromosome.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine().getTime();
                        mec.get(i).add(chromosome.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine());
                    }
                }
                chromosome.getDNA().get(i).setTime(times);
                lastTime.add(times);
            }
        }
        //并发
        int v = mec.get(0).size();
        for (List<Machine> machines : mec) {
            if (v <= machines.size())
                v = machines.size();
        }
        for (int i = 0; i < mec.size(); i++) {
            if (mec.get(i).size() != v) {
                int r = v - mec.get(i).size();
                for (int j = 0; j < r; j++) {
                    String a = "a";
                    mec.get(i).add(new Machine(a));
                }

            }
        }
        boolean set = true;
        int[] a = new int[3];
        int e = 0;
        while (set) {
            for (int i = 0; i < mec.size(); i++) {
                int time_asd = a[i];
                for (int j = 0; j < machineList.size(); j++) {
                    if (machineList.get(j).getName().equals(mec.get(i).get(e).getName())) {
                        if (machineList.get(j).getTime() <= time_asd) {
                            int q = mec.get(i).get(e).getTime();
                            machineList.get(j).setTime(q + time_asd);
                            a[i] = a[i] + q;
                        } else {
                            int p = machineList.get(j).getTime();
                            a[i] = machineList.get(j).getTime() + mec.get(i).get(e).getTime();
                            machineList.get(j).setTime(a[i]);

                        }
                    }
                }
            }
            e = e + 1;
            if (v == e)
                set = false;
        }
        int times = 0;
        for (int i = 0; i < 3; i++) {
            if (times <= a[i])
                times = a[i];
        }
        for (int i = 0; i < chromosome.getDNA().size(); i++) {
            chromosome.getDNA().get(i).setTime(a[i]);
        }
        chromosome.setTime(times);
        //比较选取最大的为执行时间time
    }

    public Integer DecodeQ_bit(List<Integer> as, WorkStation workStation) {
        Chromosome chromosome1 = new Chromosome();
//        Chromos chromosome1 = new Chromos();
        chromosome1.DNA.clear();
        List<WorkPiece> a = workStation.getWorkPieceList();
//       System.out.println(a.size());
//        System.out.println(a);
//        Collections.shuffle(a);
//        System.out.println(a);
//        System.out.println(a.size());
        chromosome1.DNA = new ArrayList<>();
        chromosome1.DNA.add(new AGV("AGV1"));
        chromosome1.DNA.add(new AGV("AGV2"));
        chromosome1.DNA.add(new AGV("AGV3"));
        chromosome1.setTime(0);
        /*
         * @ i 0 - 7   0-7
         * @ j 8 - 15  0-2
         * @ tes 16 - 42
         * */
        int s = 8;
        for (int j = 0; j < 8; j++) {
            chromosome1.DNA.get(as.get(s++)).getJopList().add(a.get(j));
        }
        int tes = 16;
        for (int i = 0; i < chromosome1.DNA.size(); i++) {
            for (int j = 0; j < chromosome1.DNA.get(i).getJopList().size(); j++) {
                for (int k = 0; k < chromosome1.DNA.get(i).getJopList().get(j).getProcessList().size(); k++) {
                    chromosome1.DNA.get(i).getJopList().get(j).getProcessList().get(k).set_machine(as.get(tes++));
                }
            }
        }
        //计算当前时间  解码
        chromosome1.setTime(0);
        Decode.code(workStation, chromosome1);

        return chromosome1.getTime();
    }
}
