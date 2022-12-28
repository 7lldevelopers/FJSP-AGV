package GA;

import com.sun.xml.internal.ws.server.provider.SyncProviderInvokerTube;
import util.WorkStation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenAlgorithm {
    //初始列表
    private static List<Chromosome> population = new ArrayList<>();
    //种群列表
    private static List<Chromosome> populationList=new ArrayList<>();
    private int popSize = 30;//种群数量
    private int maxIterNum ;//最大迭代次数
    private int generation = 1;//当前遗传到第几代
    private int multiple=3;//倍数
    private int someTime=500;//种群中最优解出现相同的时间解集  退出循环
    private double bestTime;//最好时间  局部
    private double averageTime;//平均时间
    private Chromosome good;//历史种群中最好的AGV调度
    private int geneI;//种群中最好的AGV调度  所在代数
    public void genStart(WorkStation workStation){
        //生成随机的第一代种群
        population.clear();
        for (int i = 0; i <30; i++) {
            population.add(Chromosome.Create(workStation));
        }
//        for (int i = 0; i < 30; i++) {
//            Decode.code(workStation,population.get(i));
//        }
//        for (int i = 0; i < popSize; i++) {
//            System.out.println("``````````````````````````````````````````````````");
//            System.out.println(population.get(i).getDNA().size());
//            System.out.println(population.get(i).getDNA().get(0).getJopList().size());
//            System.out.println(population.get(i).getDNA().get(1).getJopList().size());
//            System.out.println(population.get(i).getDNA().get(2).getJopList().size());
//        }
        //排序
        Collections.sort(population);
        List<Chromosome> Best=new ArrayList<>();
        boolean allSame=false;
        int e=0;
        //当出现相同的解及最短的时间 迭代结束
        while (!allSame){
            generation++;
//            System.out.println(generation+"迭代次数");
//          System.out.println(generation);
            populationList.clear();
            //杂交  变异得到新的种群3*30=90
            for (int i = 0; i <multiple ; i++) {
                for (int j = 0; j < population.size(); j++) {
                    //杂交*************
                    Chromosome newCH=population.get(j).cross(population.get(j));
                    //变异*************
                    newCH.variation(newCH);
                    populationList.add(newCH);
                }
            }
            //解码 计算时间
            for (Chromosome c :
                    populationList) {
                Decode.code(workStation,c);
            }
            //将上一代加入到种群
            for (int i = 0; i < population.size(); i++) {
                populationList.add(population.get(i));
            }
            //对种群排序
            Collections.sort(population);
            Collections.sort(populationList);
            //选取最优的为新种群
            for (int i = 0; i < population.size(); i++) {
                population.set(i,populationList.get(i));
            }
            //将每一代最优解 存储
            Best.add(population.get(0));
            //连续出现相同的解大于最有数
            if (Best.size()+1>=someTime){
                Best.remove(0);
            }
            //循环终止的条件 出现10次相同的数据 及停止
            allSame=true;
            for (int i = 0; i < Best.size(); i++) {
                if (!(Best.get(0).getTime()==Best.get(i).getTime())){
                    allSame=false;
                }else {
                    continue;
                }
            }
            //+1   ************************************
            if (Best.size()<someTime) allSame=false;
//            for (int i = 0; i < Best.size(); i++) {
//                System.out.println(Best.get(i).getTime());
//            }
            System.out.println(Best.get(0).getTime());
            //
            List<Chromosome> ch=new ArrayList<>();

//            if (generation%500==0){
//                ch.add(Best.get(0));
//            }
//            if (ch.size()>2)
//            if (ch.get(e).getTime()==ch.get(e++).getTime())
        }
//        System.out.println("===============================================================");
//        System.out.println(good);
        good=Best.get(0);System.out.println(generation);
        System.out.println(good.getTime());
        for (int i = 0; i < good.getDNA().size(); i++) {
            System.out.print("第"+i+"车AGV"+"目标：");
            System.out.println("执行个数"+good.getDNA().get(i).getJopList().size());
            for (int j = 0; j < good.getDNA().get(i).getJopList().size(); j++) {
                for (int k = 0; k <good.getDNA().get(i).getJopList().get(j).getProcessList().size() ; k++) {
                    System.out.print("="+good.getDNA().get(i).getJopList().get(j).getProcessList().get(k).getMachine().getName()+"=");
                }

            }
        }
//        System.out.println(good.getDNA().get(0).getJopList().size());
//        System.out.println("===============================================================");
    }
    private int geneSize;//基因最大长度
    private double crossRate = 0.6;
    private double mutationRate = 0.01;//基因变异的概率
    private int maxMutationNum = 3;//最大变异次数

}
