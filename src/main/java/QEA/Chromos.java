package QEA;

import vo.AGV;

import java.util.ArrayList;
import java.util.List;

public class Chromos {

    public double score;

    public List<AGV> DNA=new ArrayList<>();

    public int time;

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
}
