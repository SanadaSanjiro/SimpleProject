package com.digdes.simple.file;

import com.digdes.simple.project.ProjectModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name="files")
public class FileModel {
    @EmbeddedId
    private FileKey key;
    private String name;
    @ManyToOne
    @MapsId("prjcode")
    @JoinColumn(name = "prjcode")
    private ProjectModel project;
}
