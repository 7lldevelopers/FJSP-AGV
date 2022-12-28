package map;

public class Node implements Comparable<Node>{
    private int x;
    private int y;
    private int G;
    private int F;
    private int H;
    private Node Father ;
    public Node(int x,int y){
        this.x=x;
        this.y=y;
    }
    public void initNode(Node father,Node end){
        this.Father=father;
        if (this.Father!=null){
            this.G=father.getG()+1;
        }else {
            this.G=0;
        }
        //计算 F G H E 的值；
        this.H=Math.abs(this.x-end.getX())+Math.abs(this.y-end.getY());
        this.F=this.G+this.H;
    }
    @Override
    public int compareTo(Node node) {
        return Integer.compare(this.F,node.getF());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public int getF() {
        return F;
    }

    public void setF(int f) {
        F = f;
    }

    public int getH() {
        return H;
    }

    public void setH(int h) {
        H = h;
    }

    public Node getFather() {
        return Father;
    }

    public void setFather(Node father) {
        Father = father;
    }
}
