package vo.runAGV;

public class Addtime {
    private int fr1;//1   y
    private int cri1;//5   z
    private int ti1;// 4/fr1  X
    private int x;
    private int y;

    public Addtime(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Addtime(int fr1, int cri1, int x, int y) {
        this.fr1 = fr1;
        this.cri1 = cri1;
        this.x = x;
        this.y = y;
        this.ti1=4/fr1;
    }

    public int getFr1() {
        return fr1;
    }

    public void setFr1(int fr1) {
        this.fr1 = fr1;
    }

    public int getCri1() {
        return cri1;
    }

    public void setCri1(int cri1) {
        this.cri1 = cri1;
    }

    public int getTi1() {
        return ti1;
    }

    public void setTi1(int ti1) {
        this.ti1 = ti1;
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
}
