
package GA;
import java.util.*;
import com.alibaba.fastjson.JSONObject;
import readYml.ReadYml;
import util.WorkPiece;
import util.WorkStation;
import vo.AGV;

import java.util.*;
/*
* 一条染色体 代表一个任务序列
* 一个是 工件搜索  一个是路径搜索 */
public class Chromosome implements Comparable<Chromosome>{
    //这是染色体的得分。 ------修改public private
    public double score;
    //存入工件标号。AGV一次运输 一个工件 工序自动确定 public
    public  List<AGV> DNA=new ArrayList<>();

    //AGV对应工件的
    //该染色体AGV全部运行完成时间。
    public int time;
    //初始化AGV序列 随机生成
    //生出初始的DNA序列随机给AGV分配订单 =随机数=
    public static Chromosome Create(WorkStation workStation){
        Chromosome chromosome1=new Chromosome();
        chromosome1.DNA.clear();
        List<WorkPiece> a=workStation.getWorkPieceList();
//       System.out.println(a.size());
//        System.out.println(a);
        Collections.shuffle(a);
//        System.out.println(a);
//        System.out.println(a.size());
        Random random=new Random();
        chromosome1.DNA=new ArrayList<>();
        chromosome1.DNA.add(new AGV("AGV1"));
        chromosome1.DNA.add(new AGV("AGV2"));
        chromosome1.DNA.add(new AGV("AGV3"));
        chromosome1.setTime(0);
        for (int i = 0; i < a.size(); i++) {
                chromosome1.DNA.get(random.nextInt(3)).getJopList().add(a.get(i));
        }
        for (int i = 0; i <chromosome1.DNA.size() ; i++) {
            for (int j = 0; j <chromosome1.DNA.get(i).getJopList().size() ; j++) {
                for (int k = 0; k <chromosome1.DNA.get(i).getJopList().get(j).getProcessList().size() ; k++) {
                    chromosome1.DNA.get(i).getJopList().get(j).getProcessList().get(k).set_machine();
                }
            }
        }
        //计算当前时间  解码
        chromosome1.setTime(0);
        Decode.code(workStation,chromosome1);
        //List<AGV> runAGV =workStation.getAgvList();
    return chromosome1;
    }
    //交叉AGV的任务序列
    public Chromosome cross(Chromosome another){
        Chromosome newCh=new Chromosome();
        newCh.DNA.add(new AGV("AGV1"));
        newCh.DNA.add(new AGV("AGV2"));
        newCh.DNA.add(new AGV("AGV3"));
        for (int i = 0; i < newCh.DNA.size(); i++) {
            for (int j = 0; j < another.DNA.get(i).getJopList().size(); j++) {
                newCh.DNA.get(i).getJopList().add(another.DNA.get(i).getJopList().get(j));
            }

        }
        //q确定交叉的AGV
        int max,min;
        int timeMax,timeMin;
        if (another.DNA.get(0).getTime()>another.DNA.get(1).getTime()){
            timeMax=another.DNA.get(0).getTime();
            timeMin=another.DNA.get(1).getTime();
            max=0;min=1;
        }else {
            timeMax=another.DNA.get(1).getTime();
            timeMin=another.DNA.get(0).getTime();
            max=1;min=0;
        }
        for (int i = 2; i < another.DNA.size(); i++) {
            if (timeMax<another.DNA.get(i).getTime()){
                timeMax=another.DNA.get(i).getTime();max=i;}
            if (timeMin>another.DNA.get(i).getTime()){
                timeMin=another.DNA.get(i).getTime();min=i;}
        }
        //AGV执行最长的任务中 和最短的交叉
        int start=(int)(Math.random()*(another.DNA.get(max).getJopList().size()));
        int length=(int)(Math.random()*(another.DNA.get(max).getJopList().size()-start-1)+1);
        //选取最短的AGV 进行插入
        int start1=(int)(Math.random()*(another.DNA.get(min).getJopList().size()));
        List<WorkPiece> part=new ArrayList(another.DNA.get(max).getJopList().subList(start,start+length));
//        System.out.println(start);
//        System.out.println(part.size());
//        System.out.println(another.DNA.get(min).getJopList());
//        System.out.println(start1);
        for (int i = 0; i <part.size(); i++) {
            newCh.DNA.get(min).getJopList().add(start1,part.get(i));
        }
        if (part.size()==0){
            return newCh;
        }else {
            for (int i = 0; i < part.size(); i++) {
                newCh.DNA.get(max).getJopList().remove(start);
            }
        }
        return newCh;
    }
    //变异--机器的选择
    public void variation(Chromosome another){
        int max,min;
        int timeMax,timeMin;
        if (another.DNA.get(0).getTime()>another.DNA.get(1).getTime()){
            timeMax=another.DNA.get(0).getTime();
            timeMin=another.DNA.get(1).getTime();
            max=0;min=1;
        }else {
            timeMax=another.DNA.get(1).getTime();
            timeMin=another.DNA.get(0).getTime();
            max=1;min=0;
        }
        for (int i = 2; i < another.DNA.size(); i++) {
            if (timeMax<another.DNA.get(i).getTime())
                timeMax=another.DNA.get(i).getTime();max=i;
            if (timeMin>another.DNA.get(i).getTime())
                timeMin=another.DNA.get(i).getTime();min=i;
        }
        int start=(int)(Math.random()*(another.DNA.get(max).getJopList().size()-1));
        int length=(int)(Math.random()*(another.DNA.get(max).getJopList().size()-start-1)+1);
        Random random=new Random();
        for (int i = start; i <start+length ; i++) {
            for (int j = 0; j <another.DNA.get(max).getJopList().get(start).getProcessList().size() ; j++) {
                another.DNA.get(max).getJopList().get(start).getProcessList().get(j).set_machine();
            }
        }
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<AGV> getDNA() {
        return DNA;
    }

    public void setDNA(List<AGV> DNA) {
        this.DNA = DNA;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int[] RandomInt(int[] a){
        Random r=new Random();
        for (int i = 0; i < a.length; i++) {
            int randomInts=r.nextInt(a.length);
            int temp =a[i];
            a[i]=a[randomInts];
            a[randomInts]=temp;
        }
        return a;
    }
    public static void main(String[] args) {
        Random random=new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(4));
        }
    }
    //通过时间 排序
    @Override
    public int compareTo(Chromosome chromosome) {
        if (time>chromosome.time) {
            return 1;
        }else if (time==chromosome.time){
            return 0;
        }else {
            return -1;
        }
    }
    //tostring

    @Override
    public String toString() {
        return "Chromosome{" +
                "score=" + score +
                ", DNA=" + DNA +
                ", time=" + time +
                '}';
    }
}
