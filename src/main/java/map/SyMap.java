package map;

public class  SyMap {
    private int[][] map={
            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,0,0,0,0,0,0,0,-1},
            {-1,0,-1,0,-1,0,-1,0,-1},
            {-1,0,0,0,0,0,0,0,-1},
            {-1,0,-1,0,-1,0,-1,0,-1},
            {-1,0,0,0,0,0,0,0,-1},
            {-1,0,-1,0,-1,0,-1,0,-1},
            {-1,0,0,0,0,0,0,0,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1}
    };


    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public static void main(String[] args) {
        int a=19, b=49;
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
        System.out.println(ax+"  "  +ay);
        System.out.println(bx+"  "  +by);
    }
}
