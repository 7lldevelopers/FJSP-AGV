package vo.factory;


import org.jfree.data.gantt.Task;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriod;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskS implements Cloneable, PublicCloneable, Serializable {
    private static final long serialVersionUID = 1094303785346988894L;
    private String description;
    private TimePeroid duration;
    private Double percentComplete;
    private List subtasks;

    public TaskS(String description, TimePeroid duration) {
        if (description == null) {
            throw new IllegalArgumentException("Null 'description' argument.");
        } else {
            this.description = description;
            this.duration = duration;
            this.percentComplete = null;
            this.subtasks = new ArrayList();
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Null 'description' argument.");
        } else {
            this.description = description;
        }
    }

    public TimePeroid getDuration() {
        return this.duration;
    }

    public void setDuration(TimePeroid duration) {
        this.duration = duration;
    }

    public Double getPercentComplete() {
        return this.percentComplete;
    }

    public void setPercentComplete(Double percent) {
        this.percentComplete = percent;
    }

    public void setPercentComplete(double percent) {
        this.setPercentComplete(new Double(percent));
    }

    public void addSubtask(TaskS subtask) {
        if (subtask == null) {
            throw new IllegalArgumentException("Null 'subtask' argument.");
        } else {
            this.subtasks.add(subtask);
        }
    }

    public void removeSubtask(org.jfree.data.gantt.Task subtask) {
        this.subtasks.remove(subtask);
    }

    public int getSubtaskCount() {
        return this.subtasks.size();
    }

    public TaskS getSubtask(int index) {
        return (TaskS)this.subtasks.get(index);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof org.jfree.data.gantt.Task)) {
            return false;
        } else {
            vo.factory.TaskS that = (vo.factory.TaskS)object;
            if (!ObjectUtilities.equal(this.description, that.description)) {
                return false;
            } else if (!ObjectUtilities.equal(this.duration, that.duration)) {
                return false;
            } else if (!ObjectUtilities.equal(this.percentComplete, that.percentComplete)) {
                return false;
            } else {
                return ObjectUtilities.equal(this.subtasks, that.subtasks);
            }
        }
    }
    public Object clone() throws CloneNotSupportedException {
        org.jfree.data.gantt.Task clone = (org.jfree.data.gantt.Task)super.clone();
        return clone;
    }
}