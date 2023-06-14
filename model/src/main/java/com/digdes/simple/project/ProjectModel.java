package com.digdes.simple.project;

import com.digdes.simple.file.FileModel;
import com.digdes.simple.task.TaskModel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name="projects")
public class ProjectModel implements Serializable {
    @Id
    private String code;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String uuid; //не используется, заложено для следующих версий программы
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @OneToMany
    @JoinColumn(name = "prjcode")
    private Set<FileModel> files = new HashSet<>();

    @Override
    public String toString() {
        return "ProjectModel{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                "}\n";
    }
}
