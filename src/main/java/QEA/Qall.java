package QEA;

import GA.Decode;
import com.alibaba.fastjson.JSONArray;
import util.WorkStation;

import java.nio.file.Watchable;
import java.util.*;

public class Qall {
    private int generation=8000;//迭代次数
    private int Q_size=86;//Qbit 长度 16+16+54=86
    private int Q_pop=50;//种群数量
    private int BestScore=0;//最好的适应值
    private List<List<Qubit>> Qlist=new ArrayList<>();//种群Qbit
    private List<List<Integer>> Slist=new ArrayList<>();//解码得到的种群
    private List<List<Integer>> Dlist=new ArrayList<>();
    private List<Integer> Best=new ArrayList<>();//最好的一个结果 保存
    private List<Qubit>  BestQubit=new ArrayList<>();//
    private List<Integer> score=new ArrayList<>();//存储种群的得分情况
    //初始化Qbit
    public void Qinit(){
        for (int i = 0; i < Q_pop; i++) {
            List<Qubit> q_list=new ArrayList<>();
            for (int j = 0; j < Q_size; j++) {
                Double a=Math.sqrt(0.5);
                Double b=Math.sqrt(0.5);
                System.out.println(a);
                Qubit q=new Qubit(a,b);
                q_list.add(q);
            }
            Qlist.add(q_list);
        }
    }
    //生成二进制解
    public List<List<Integer>> make(){
        List<List<Integer>> slist=new ArrayList<>();
        for (int j = 0; j < Q_pop; j++) {
            List<Integer> s_list=new ArrayList<>();
            for (int i = 0; i < Q_size; i++) {
                Random R=new Random();
                if (R.nextDouble()<Math.pow(Qlist.get(j).get(i).getA(),2)){
                    s_list.add(1);
                }else {
                    s_list.add(0);
                }
            }
            slist.add(s_list);
        }
        return slist;
    }
    //对二进制进行解码 得到订单的执行顺序
    public List<List<Integer>> Decode(List<List<Integer>> a){
        List<List<Integer>> c=new ArrayList<>();
        //变换格式
        for (List<Integer> ear: a){
            List<Integer> f=new ArrayList<>();
            for (int i = 1; i < 16;i=i+2 ) {
                switch (ear.get(i-1)){
                    case 0:switch(ear.get(i)){
                        case 0:{
                            f.add(0);
                            break;
                        }
                        case 1:{
                            f.add(1);
                            break;
                        }

                    }
                    break;
                    case 1:switch(ear.get(i)){
                        case 0:{
                            f.add(2);
                            break;
                        }
                        case 1:{
                            f.add(3);
                            break;
                        }
                    }
                    break;
                }
            }
            List<Integer> set=new ArrayList<>();
            for (int i = 0; i < f.size(); i++) {
                set.add(0);
            }
            int init=0;
            // 只存在 0 1 2 3
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < f.size(); j++) {
                    if(i==f.get(j)){
                        set.set(j,init++);
                    }
                }
            }
            for (int i = 17; i < 32; i=i+2) {
                switch (ear.get(i-1)){
                    case 0:switch(ear.get(i)){
                        case 0:{
                            set.add(0);
                            break;
                        }
                        case 1:{
                            set.add(1);
                            break;
                        }

                    }
                        break;
                    case 1:switch(ear.get(i)){
                        case 0:{
                            set.add(2);
                            break;
                        }
                        case 1:{
                            set.add(0);
                            break;
                        }
                    }
                        break;
                }
            }
            for (int i = 33; i < ear.size(); i=i+2) {
                switch (ear.get(i-1)){
                    case 0:switch(ear.get(i)){
                        case 0:{
                            set.add(0);
                            break;
                        }
                        case 1:{
                            set.add(1);
                            break;
                        }

                    }
                        break;
                    case 1:switch(ear.get(i)){
                        case 0:{
                            set.add(2);
                            break;
                        }
                        case 1:{
                            set.add(3);
                            break;
                        }
                    }
                        break;
                }
            }
            c.add(set);
        }
        return c;
    }
//              更新公式
//    对于值的更新最重要的是角度，这里的角度是有一个表的
//    x       b       f(x)<=f(b)      角度       旋转方向
//    0       0         false          0           0
//    0       0         true           0           0
//    0       1         false          0.01π       +
//    0       1         true           0           0
//    1       0         false          -0.01π      -
//    1       0         true           0           0
//    1       1         false          0           0
//    1       1         true           0           0
//    更新Qbit
    public void judge_Updata_Qubit(){
        for (int i=0;i<Slist.size();i++) {
                for (int j = 0; j < Best.size(); j++) {
                    Double tempB=Qlist.get(i).get(j).getB();
                    Double tempA=Qlist.get(i).get(j).getA();
                    switch (Slist.get(i).get(j)){
                        case 0:{switch (Best.get(j)){
                            case 0:{
                                // 00 假  真
                                if(score.get(i)<=BestScore){
                                    //更新算法   修改地方
//                                    Double As=tempA*Math.cos(0.01*Math.PI)-Math.sin(0.01*Math.PI)*tempB;
//                                    Qlist.get(i).get(j).setA(As);
//                                    Qlist.get(i).get(j).setB(Math.sqrt(1-Math.pow(As,2)));
                                }else {
                                    //更新算法   修改地方
                                    Double As=tempA*Math.cos(-0.01*Math.PI)-Math.sin(-0.01*Math.PI)*tempB;
                                    Qlist.get(i).get(j).setA(As);
                                    Qlist.get(i).get(j).setB(Math.sqrt(1-Math.pow(As,2)));
                                }
                                break;
                            }
                            case 1:{
                                // 01 假  真
                                if(score.get(i)<=BestScore){
                                    //更新算法   修改地方
                                    Double As=tempA*Math.cos(0.01*Math.PI)-Math.sin(0.01*Math.PI)*tempB;
                                    Qlist.get(i).get(j).setA(As);
                                    Qlist.get(i).get(j).setB(Math.sqrt(1-Math.pow(As,2)));
                                }else {
                                    //更新算法   修改地方
                                    Double As=tempA*Math.cos(0.01*Math.PI)-Math.sin(0.01*Math.PI)*tempB;
                                    Qlist.get(i).get(j).setA(As);
                                    Qlist.get(i).get(j).setB(Math.sqrt(1-Math.pow(As,2)));
                                }
                                break;
                            }
                        }
                            break;
                        }
                        case 1:{switch (Best.get(j)){
                            case 0:{
                                //01  真假
                                if(score.get(i)<=BestScore){
                                    //更新算法   修改地方
                                    Double As=tempA*Math.cos(-0.01*Math.PI)-Math.sin(-0.01*Math.PI)*tempB;
                                    Qlist.get(i).get(j).setA(As);
                                    Qlist.get(i).get(j).setB(Math.sqrt(1-Math.pow(As,2)));
                                }else {
                                    //更新算法   修改地方
                                    Double As=tempA*Math.cos(0.01*Math.PI)-Math.sin(0.01*Math.PI)*tempB;
                                    Qlist.get(i).get(j).setA(As);
                                    Qlist.get(i).get(j).setB(Math.sqrt(1-Math.pow(As,2)));
                                }
                                break;
                            }
                            case 1:{
                                //11 真假
                                if(score.get(i)<=BestScore){
                                    //更新算法   修改地方
                                    Double As=tempA*Math.cos(-0.01*Math.PI)-Math.sin(-0.01*Math.PI)*tempB;
                                    Qlist.get(i).get(j).setA(As);
                                    Qlist.get(i).get(j).setB(Math.sqrt(1-Math.pow(As,2)));
                                }else {
                                    //更新算法   修改地方
                                    Double As=tempA*Math.cos(0.01*Math.PI)-Math.sin(0.01*Math.PI)*tempB;
                                    Qlist.get(i).get(j).setA(As);
                                    Qlist.get(i).get(j).setB(Math.sqrt(1-Math.pow(As,2)));
                                }
                                break;
                            }
                        }
                            break;
                        }
                    }
                }
        }
    }
    //查找 种群中最号的结果 求解 最小化最大时间
    public void selectBest(WorkStation workStation){
        Decode decode=new Decode();
        score.clear();
        for (List<Integer> EST:Dlist
        ){
            score.add(decode.DecodeQ_bit(EST,workStation));
        }
        int temp1=score.get(0);
        int temp2=0;
        for (int i = 0; i < score.size(); i++) {
            if (score.get(i)<=temp1){
                temp1=score.get(i);
                temp2=i;
            }
        }
        //拷贝
        Collections.addAll(Best,new Integer[Slist.get(0).size()]);
        Collections.copy(Best,Slist.get(temp2));
        BestScore=temp1;
    }
    public void select_Best(WorkStation workStation){
        Decode decode1=new Decode();
        score.clear();
        for (List<Integer> EST:Dlist
        ){
            score.add(decode1.DecodeQ_bit(EST,workStation));
        }
        int temp1=score.get(0);
        int temp2=0;
        for (int i = 0; i < score.size(); i++) {
            if (score.get(i)<=temp1){
                temp1=score.get(i);
                temp2=i;
            }
        }
        if (temp1<BestScore){
            BestScore=temp1;
            Best.clear();
            Collections.addAll(Best,new Integer[Slist.get(0).size()]);
            Collections.copy(Best,Slist.get(temp2));
        }

    }
    public void q_bitStart(WorkStation workStation){
        int t=1;
        Qinit();//qbit初始化
        this.Slist=make();//转换 为 01数值--数值化
        this.Dlist=Decode(Slist);//解码 为了抄走
        selectBest(workStation);//设置初始的最好的值 Best   1. 2.（记录全局最优的序列） 查找最优质的  更新
        while(t<generation){
            System.out.println(BestScore);
            judge_Updata_Qubit();
            this.Slist=make();
            this.Dlist=Decode(Slist);
            select_Best(workStation);
            t++;
        }
    }
    public static void main(String[] args) {
        Qall qall=new Qall();
        qall.Qinit();
        //50条Qbit得分情况
        List<List<Integer>> etf= qall.Decode(qall.make());
        qall.Slist=etf;
//        qall.judge_Updata_Qubit();
        Random R=new Random();
//        for (int i = 0; i < 30; i++) {
////            System.out.println(R.nextDouble());
////            System.out.println(Math.sqrt(2));
//            System.out.println(Math.pow(9,2));
//        }
        List<Integer> a=new ArrayList<>();
        a.add(1);a.add(1);a.add(1);a.add(1);a.add(1);

        List<Integer> b=new ArrayList<>();
        b=a;
        Double c=Math.PI*3;
        System.out.println(b.get(1));
        System.out.println(c);
    }
}
