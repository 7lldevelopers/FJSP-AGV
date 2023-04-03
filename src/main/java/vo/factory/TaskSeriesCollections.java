package vo.factory;


import org.jfree.data.gantt.GanttCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.general.AbstractSeriesDataset;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.time.TimePeriod;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskSeriesCollections extends AbstractSeriesDataset implements GanttCategoryDataset, Cloneable, PublicCloneable, Serializable {
    private static final long serialVersionUID = -2065799050738449903L;
    private List keys = new ArrayList();
    private List data = new ArrayList();

    public TaskSeriesCollections() {
    }

    public TaskSeries getSeries(Comparable key) {
        if (key == null) {
            throw new NullPointerException("Null 'key' argument.");
        } else {
            TaskSeries result = null;
            int index = this.getRowIndex(key);
            if (index >= 0) {
                result = this.getSeries(index);
            }

            return result;
        }
    }

    public TaskSeries getSeries(int series) {
        if (series >= 0 && series < this.getSeriesCount()) {
            return (TaskSeries)this.data.get(series);
        } else {
            throw new IllegalArgumentException("Series index out of bounds");
        }
    }

    public int getSeriesCount() {
        return this.getRowCount();
    }

    public Comparable getSeriesKey(int series) {
        TaskSeries ts = (TaskSeries)this.data.get(series);
        return ts.getKey();
    }

    public int getRowCount() {
        return this.data.size();
    }

    public List getRowKeys() {
        return this.data;
    }

    public int getColumnCount() {
        return this.keys.size();
    }

    public List getColumnKeys() {
        return this.keys;
    }

    public Comparable getColumnKey(int index) {
        return (Comparable)this.keys.get(index);
    }

    public int getColumnIndex(Comparable columnKey) {
        if (columnKey == null) {
            throw new IllegalArgumentException("Null 'columnKey' argument.");
        } else {
            return this.keys.indexOf(columnKey);
        }
    }

    public int getRowIndex(Comparable rowKey) {
        int result = -1;
        int count = this.data.size();

        for(int i = 0; i < count; ++i) {
            TaskSeriesS s = (TaskSeriesS)this.data.get(i);
            if (s.getKey().equals(rowKey)) {
                result = i;
                break;
            }
        }

        return result;
    }

    public Comparable getRowKey(int index) {
        TaskSeriesS series = (TaskSeriesS)this.data.get(index);
        return series.getKey();
    }

    public void add(TaskSeriesS series) {
        if (series == null) {
            throw new IllegalArgumentException("Null 'series' argument.");
        } else {
            this.data.add(series);
            series.addChangeListener(this);
            Iterator iterator = series.getTasks().iterator();

            while(iterator.hasNext()) {
                TaskS task = (TaskS)iterator.next();
                String key = task.getDescription();
                int index = this.keys.indexOf(key);
                if (index < 0) {
                    this.keys.add(key);
                }
            }

            this.fireDatasetChanged();
        }
    }

    public void remove(TaskSeriesS series) {
        if (series == null) {
            throw new IllegalArgumentException("Null 'series' argument.");
        } else {
            if (this.data.contains(series)) {
                series.removeChangeListener(this);
                this.data.remove(series);
                this.fireDatasetChanged();
            }

        }
    }

    public void remove(int series) {
        if (series >= 0 && series < this.getSeriesCount()) {
            TaskSeriesS ts = (TaskSeriesS)this.data.get(series);
            ts.removeChangeListener(this);
            this.data.remove(series);
            this.fireDatasetChanged();
        } else {
            throw new IllegalArgumentException("TaskSeriesCollection.remove(): index outside valid range.");
        }
    }

    public void removeAll() {
        Iterator iterator = this.data.iterator();

        while(iterator.hasNext()) {
            TaskSeriesS series = (TaskSeriesS)iterator.next();
            series.removeChangeListener(this);
        }

        this.data.clear();
        this.fireDatasetChanged();
    }

    public Number getValue(Comparable rowKey, Comparable columnKey) {
        return this.getStartValue(rowKey, columnKey);
    }

    public Number getValue(int row, int column) {
        return this.getStartValue(row, column);
    }

    public Number getStartValue(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        int row = this.getRowIndex(rowKey);
        TaskSeriesS series = (TaskSeriesS)this.data.get(row);
        TaskS task = series.get(columnKey.toString());
        if (task != null) {
            TimePeroid duration = task.getDuration();
            if (duration != null) {
                result = new Long(duration.getStart());
            }
        }

        return result;
    }

    public Number getStartValue(int row, int column) {
        Comparable rowKey = this.getRowKey(row);
        Comparable columnKey = this.getColumnKey(column);
        return this.getStartValue(rowKey, columnKey);
    }

    public Number getEndValue(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        int row = this.getRowIndex(rowKey);
        TaskSeriesS series = (TaskSeriesS) this.data.get(row);
        TaskS task = series.get(columnKey.toString());
        if (task != null) {
            TimePeroid duration = task.getDuration();
            if (duration != null) {
                result = new Long(duration.getEnd());
            }
        }

        return result;
    }

    public Number getEndValue(int row, int column) {
        Comparable rowKey = this.getRowKey(row);
        Comparable columnKey = this.getColumnKey(column);
        return this.getEndValue(rowKey, columnKey);
    }

    public Number getPercentComplete(int row, int column) {
        Comparable rowKey = this.getRowKey(row);
        Comparable columnKey = this.getColumnKey(column);
        return this.getPercentComplete(rowKey, columnKey);
    }

    public Number getPercentComplete(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        int row = this.getRowIndex(rowKey);
        TaskSeriesS series = (TaskSeriesS)this.data.get(row);
        TaskS task = series.get(columnKey.toString());
        if (task != null) {
            result = task.getPercentComplete();
        }

        return result;
    }

    public int getSubIntervalCount(int row, int column) {
        Comparable rowKey = this.getRowKey(row);
        Comparable columnKey = this.getColumnKey(column);
        return this.getSubIntervalCount(rowKey, columnKey);
    }

    public int getSubIntervalCount(Comparable rowKey, Comparable columnKey) {
        int result = 0;
        int row = this.getRowIndex(rowKey);
        TaskSeriesS series = (TaskSeriesS)this.data.get(row);
        TaskS task = series.get(columnKey.toString());
        if (task != null) {
            result = task.getSubtaskCount();
        }

        return result;
    }

    public Number getStartValue(int row, int column, int subinterval) {
        Comparable rowKey = this.getRowKey(row);
        Comparable columnKey = this.getColumnKey(column);
        return this.getStartValue(rowKey, columnKey, subinterval);
    }

    public Number getStartValue(Comparable rowKey, Comparable columnKey, int subinterval) {
        Number result = null;
        int row = this.getRowIndex(rowKey);
        TaskSeriesS series = (TaskSeriesS)this.data.get(row);
        TaskS task = series.get(columnKey.toString());
        if (task != null) {
            TaskS sub = task.getSubtask(subinterval);
            if (sub != null) {
                TimePeroid duration = sub.getDuration();
                result = new Long(duration.getStart());
            }
        }

        return result;
    }

    public Number getEndValue(int row, int column, int subinterval) {
        Comparable rowKey = this.getRowKey(row);
        Comparable columnKey = this.getColumnKey(column);
        return this.getEndValue(rowKey, columnKey, subinterval);
    }

    public Number getEndValue(Comparable rowKey, Comparable columnKey, int subinterval) {
        Number result = null;
        int row = this.getRowIndex(rowKey);
        TaskSeriesS series = (TaskSeriesS)this.data.get(row);
        TaskS task = series.get(columnKey.toString());
        if (task != null) {
            TaskS sub = task.getSubtask(subinterval);
            if (sub != null) {
                TimePeroid duration = sub.getDuration();
                result = new Long(duration.getEnd());
            }
        }

        return result;
    }

    public Number getPercentComplete(int row, int column, int subinterval) {
        Comparable rowKey = this.getRowKey(row);
        Comparable columnKey = this.getColumnKey(column);
        return this.getPercentComplete(rowKey, columnKey, subinterval);
    }

    public Number getPercentComplete(Comparable rowKey, Comparable columnKey, int subinterval) {
        Number result = null;
        int row = this.getRowIndex(rowKey);
        TaskSeriesS series = (TaskSeriesS)this.data.get(row);
        TaskS task = series.get(columnKey.toString());
        if (task != null) {
            TaskS sub = task.getSubtask(subinterval);
            if (sub != null) {
                result = sub.getPercentComplete();
            }
        }

        return result;
    }

    public void seriesChanged(SeriesChangeEvent event) {
        this.refreshKeys();
        this.fireDatasetChanged();
    }

    private void refreshKeys() {
        this.keys.clear();

        for(int i = 0; i < this.getSeriesCount(); ++i) {
            TaskSeries series = (TaskSeries)this.data.get(i);
            Iterator iterator = series.getTasks().iterator();

            while(iterator.hasNext()) {
                Task task = (Task)iterator.next();
                String key = task.getDescription();
                int index = this.keys.indexOf(key);
                if (index < 0) {
                    this.keys.add(key);
                }
            }
        }

    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof org.jfree.data.gantt.TaskSeriesCollection)) {
            return false;
        } else {
            TaskSeriesCollections that = (TaskSeriesCollections)obj;
            return ObjectUtilities.equal(this.data, that.data);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        TaskSeriesCollections clone = (TaskSeriesCollections)super.clone();
        clone.data = (List)ObjectUtilities.deepClone(this.data);
        clone.keys = new ArrayList(this.keys);
        return clone;
    }
}