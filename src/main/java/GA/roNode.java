package GA;

public class roNode {
    private int node;
    private int cast;

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public int getCast() {
        return cast;
    }

    public void setCast(int cast) {
        this.cast = cast;
    }

    public roNode(int node, int cast) {
        this.node = node;
        this.cast = cast;
    }
    public roNode() {
    }
}
