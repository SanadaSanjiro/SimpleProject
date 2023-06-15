package com.digdes.simple.file;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TskFileKey implements Serializable {
    @Column(name="taskid")
    private Long taskid;
    @Column(name="name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TskFileKey that = (TskFileKey) o;
        return Objects.equals(taskid, that.taskid) && Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "TskFileKey{" +
                "taskid='" + taskid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskid, name);
    }
}
