package com.digdes.simple.file;

import com.digdes.simple.task.TaskModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name="taskfiles")
public class TskFileModel {
    @EmbeddedId
    private TskFileKey key;
    @ManyToOne
    @MapsId("taskid")
    @JoinColumn(name = "taskid")
    private TaskModel taskModel;
}
