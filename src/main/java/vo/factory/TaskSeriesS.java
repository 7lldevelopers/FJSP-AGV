package vo.factory;


import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.general.Series;
import org.jfree.util.ObjectUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskSeriesS extends Series {
    private List tasks = new ArrayList();

    public TaskSeriesS(String name) {
        super(name);
    }

    public void add(TaskS task) {
        if (task == null) {
            throw new IllegalArgumentException("Null 'task' argument.");
        } else {
            this.tasks.add(task);
            this.fireSeriesChanged();
        }
    }

    public void remove(Task task) {
        this.tasks.remove(task);
        this.fireSeriesChanged();
    }
    public void removeAll() {
        this.tasks.clear();
        this.fireSeriesChanged();
    }
    public int getItemCount() {
        return this.tasks.size();
    }

    public TaskS get(int index) {
        return (TaskS)this.tasks.get(index);
    }

    public TaskS get(String description) {
        TaskS result = null;
        int count = this.tasks.size();

        for(int i = 0; i < count; ++i) {
            TaskS t = (TaskS)this.tasks.get(i);
            if (t.getDescription().equals(description)) {
                result = t;
                break;
            }
        }

        return result;
    }

    public List getTasks() {
        return Collections.unmodifiableList(this.tasks);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof org.jfree.data.gantt.TaskSeries)) {
            return false;
        } else if (!super.equals(obj)) {
            return false;
        } else {
            TaskSeriesS that = (TaskSeriesS)obj;
            return this.tasks.equals(that.tasks);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        TaskSeriesS clone = (TaskSeriesS) super.clone();
        clone.tasks = (List) ObjectUtilities.deepClone(this.tasks);
        return clone;
    }
}

