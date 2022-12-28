package map;
import java.util.ArrayList;
import java.util.PriorityQueue;
public class Maprea {
    //开始位置  结束位置
    private Node start;
    private Node end;
    //路径
    private ArrayList<Node> road=new ArrayList<>();
    public void setStart(Node starte, Node node){
        this.start=starte;
        this.end=node;
    }
     public void start(){
        Solution solution=new Solution();
        Node resNode=solution.asterSearch(start,end);
        while (resNode!=null){
            road.add(new Node(resNode.getX(),resNode.getY()));
            resNode=resNode.getFather();
        }
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
    public static void main(String[] args) {
        int[][] map={
                {-1,-1,-1,-1,-1,-1,-1},
                {-1,0,0,0,0,0,-1},
                {-1,0,0,0,0,0,-1},
                {-1,0,0,0,0,0,-1},
                {-1,0,-1,-1,-1,-1,-1},
                {-1,0,0,0,0,0,-1},
                {-1,-1,-1,-1,-1,-1,-1}
        };
        Maprea maprea=new Maprea();
        Node sta=new Node(3,3);
        Node end=new Node(5,5);
        maprea.setStart(sta,end);
        maprea.start();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.printf("%3d",map[i][j]);
            }
            System.out.println();
        }
        for (int i = maprea.getRoad().size()-1; i >= 0; i--) {
            System.out.println(" ("+maprea.getRoad().get(i).getX()+maprea.getRoad().get(i).getY()+") ");
        }
    }
}
