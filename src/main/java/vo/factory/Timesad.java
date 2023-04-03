package vo.factory;

import org.jfree.data.time.TimePeriod;

import java.io.Serializable;
import java.util.Date;

public class Timesad implements  TimePeroid, Comparable, Serializable {

    private static final long serialVersionUID = 8684672361131829554L;
    private Integer start;
    private Integer end;

    public Timesad (Integer start, Integer end) {
        if (start > end) {
            throw new IllegalArgumentException("Requires start <= end.");
        } else {
            this.start = start;
            this.end = end;
        }
    }
    @Override
    public int compareTo(Object o) {
        TimePeroid that = (TimePeroid)o;
        long t0 = this.getStart();
        long t1 = this.getEnd();
        long m0 = t0 + (t1 - t0) / 2L;
        long t2 = that.getStart();
        long t3 = that.getEnd();
        long m1 = t2 + (t3 - t2) / 2L;
        if (m0 < m1) {
            return -1;
        } else if (m0 > m1) {
            return 1;
        } else if (t0 < t2) {
            return -1;
        } else if (t0 > t2) {
            return 1;
        } else if (t1 < t3) {
            return -1;
        } else {
            return t1 > t3 ? 1 : 0;
        }
    }

    @Override
    public Integer getStart() {
        return start;
    }

    @Override
    public Integer getEnd() {
        return end;
    }
    public long getStartMillis() {
        return this.start;
    }
    public long getEndMillis() {
        return this.end;
    }
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof TimePeriod)) {
            return false;
        } else {
            TimePeriod that = (TimePeriod)obj;
            if (!this.getStart().equals(that.getStart())) {
                return false;
            } else {
                return this.getEnd().equals(that.getEnd());
            }
        }
    }
//
//    public int hashCode() {
//        int result = 17;
//        int result = 37 * result + (int)this.start;
//        result = 37 * result + (int)this.end;
//        return result;
//    }

}
