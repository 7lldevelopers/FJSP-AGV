package map;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Solution {
    //优先队列
    private PriorityQueue<Node> open=new PriorityQueue<Node>();
    //存储的节点
    private ArrayList<Node> close=new ArrayList<Node>();
    //出现过的节点
    private ArrayList<Node> exist=new ArrayList<Node>();
    //地图资源
    private int[][] map={
            {-1,-1,-1,-1,-1,-1,-1},
            {-1,0,0,0,0,0,-1},
            {-1,0,0,0,0,0,-1},
            {-1,0,0,0,0,0,-1},
            {-1,0,-1,-1,-1,-1,-1},
            {-1,0,0,0,0,0,-1},
            {-1,-1,-1,-1,-1,-1,-1}
    };
    public Node asterSearch(Node s,Node e){
        this.open.add(s);
        this.exist.add(s);
        while (open.size()>0){
            Node currentNode=open.poll();
            close.add(currentNode);
            //当前节点的四周节点
            ArrayList<Node> neighbour=extendCurrent(currentNode);
            for (Node n :
                    neighbour) {
                if (n.getX()==e.getX() && n.getY() == e.getY()){
                    n.initNode(currentNode,e);
                    return n;
                }
                if (!isExist(n)){
                    n.initNode(currentNode,e);
                    open.add(n);
                    exist.add(n);
                }
            }
        }
        return null;
    }
    public ArrayList<Node> extendCurrent(Node node){
        int x= node.getX();
        int y=node.getY();
        ArrayList<Node> neighbourNode=new ArrayList<>();
        if (isValid(x+1,y)){
            Node node1=new Node(x+1,y);
            neighbourNode.add(node1);
        }
        if (isValid(x-1,y)){
            Node node1=new Node(x-1,y);
            neighbourNode.add(node1);
        }
        if (isValid(x,y-1)){
            Node node1=new Node(x,y-1);
            neighbourNode.add(node1);
        }
        if (isValid(x,y+1)){
            Node node1=new Node(x,y+1);
            neighbourNode.add(node1);
        }
        return neighbourNode;
    }
    public boolean isValid(int x,int y) {
        if (map[x][y] == -1) return false;
        for (Node n :
                exist) {
            if (isExist(new Node(x, y))) {
                return false;
            }
        }
        return true;
    }
    public boolean isExist(Node node){
        for (Node existNode :
                exist) {
            if (node.getX() == existNode.getX() && node.getY() == existNode.getY()) {
                return true;
            }
        }
        return false;
    }
}
