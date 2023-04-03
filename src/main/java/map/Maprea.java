package map;
import GA.roNode;
import jdk.internal.dynalink.linker.LinkerServices;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Maprea {
    //开始位置  结束位置
    private Node start;
    private Node end;
    private List<Integer> reNode=new ArrayList<>();
    //路径
    private ArrayList<Node> road=new ArrayList<>();
    //返回的节点 带价值
   private List<roNode> locst=new ArrayList<>();
    public void setStart(Node starte, Node node){
        this.start=starte;
        this.end=node;
    }
     public Maprea(int a,int b){
         int ax,ay;
         int bx,by;
         if(a%7==0){
             ax=a/7;
             ay=7;
         }else {
             ax=a/7+1;
             ay=a%7;
         }
         if(b%7==0){
             bx=b/7;
             by=7;
         }else {
             bx=b/7+1;
             by=b%7;
         }
         Node sta=new Node(ax, ay);
         Node end1=new Node(bx, by);
         start=sta;
         end=end1;
        Solution solution=new Solution();
        Node resNode=solution.asterSearch(sta,end1);
        while (resNode!=null){
            road.add(new Node(resNode.getX(),resNode.getY()));
            resNode=resNode.getFather();
        }
         for (int i = 0; i < road.size(); i++) {
             reNode.add(0,(road.get(i).getX()-1)*7+(road.get(i).getY()));

         }
         for (int i = 0; i < reNode.size(); i++) {
             //设置路径中运输 时间。map[][]来返回路径的执行时间。
             locst.add(new roNode( reNode.get(i),2));
         }
     }

    public List<roNode> getLocst() {
        return locst;
    }

    public List<Integer> getReNode() {
        return reNode;
    }
    public void setReNode(List<Integer> reNode) {
        this.reNode = reNode;
    }
    public Node getStart() {
        return start;
    }
    public void setStart(Node start) {
        this.start = start;
    }
    public Node getEnd() {
        return end;
    }
    public void setEnd(Node end) {
        this.end = end;
    }
    public ArrayList<Node> getRoad() {
        return road;
    }
    public void setRoad(ArrayList<Node> road) {
        this.road = road;
    }

    //返回节点 String  Name  来确定
public void return_path(int a, int b){

    //定义机器的位置 确定位置集合
    //  M1  M2  M3  M4
    int ax,ay;
    int bx,by;
    if(a%7==0){
        ax=a/7;
        ay=7;
    }else {
        ax=a/7+1;
        ay=a%7;
    }
    if(b%7==0){
        bx=b/7;
        by=7;
    }else {
        bx=b/7+1;
        by=b%7;
    }
    Maprea maprea=new Maprea(1,1);
    Node sta=new Node(ax, ay);
    Node end=new Node(bx, by);
    maprea.setStart(sta,end);
 //   maprea.start();
    for (int i = 0; i < maprea.getRoad().size(); i++) {
        reNode.add(0,(maprea.getRoad().get(i).getX()-1)*4+(maprea.getRoad().get(i).getY()));

    }
    //除去  开始 和结束的节点
    locst=new ArrayList<>();
    for (int i = 0; i < maprea.getReNode().size(); i++) {
        //设置路径中运输 时间。
        locst.add(new roNode(maprea.getReNode().get(i),2));
    }
    //返回节点的内容。 及路径的时间。
}



    @SneakyThrows
    public static void main(String[] args) {
        //确定共享节点。 -1 该节点被占用。 0可以通行
        int[][] map1={
                {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,0,0,0,0,0,0,0,-1},
                {-1,0,0,0,0,0,0,0,-1},
                {-1,0,0,0,0,0,0,0,-1},
                {-1,0,11,11,11,11,0,0,-1},
                {-1,0,0,0,0,0,0,0,-1},
                {-1,0,0,0,0,0,0,0,-1},
                {-1,0,0,0,0,0,0,0,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1}
        };

        Node sta=new Node(3,3);
        Node end=new Node(5,5);

        //反射
        Class maprea=Class.forName("map.Maprea");
        Method setStart=maprea.getMethod("setStart", Node.class, Node.class);
        Constructor appleConstructor = maprea.getConstructor();
        Object mapObj=appleConstructor.newInstance();
        setStart.invoke(mapObj,sta,end);
        Method Start=mapObj.getClass().getMethod("start");
        Start.invoke(mapObj);
       // Method SgetRoad=mapObj.getClass().getMethod("return_map");
        int a=1,b=2;
      //  SgetRoad.invoke(mapObj);
        //反射返回的是list 需要
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.printf("%3d",map1[i][j]);
            }
            System.out.println();
        }
        Maprea maprea1=new Maprea(1,1);
        Node sta1=new Node(3, 3);
        Node end1=new Node(5,5);
        maprea1.setStart(sta1,end1);
      //  maprea1.start();
        for (int i = 0; i < maprea1.getRoad().size(); i++) {
            maprea1.reNode.add(0,(maprea1.getRoad().get(i).getX()-1)*7+(maprea1.getRoad().get(i).getY()));
        }

    }
}
