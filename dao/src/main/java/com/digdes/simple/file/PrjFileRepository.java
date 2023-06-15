package com.digdes.simple.file;

import com.digdes.simple.project.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrjFileRepository extends JpaRepository<PrjFileModel, PrjFileKey> {
    Optional<List<PrjFileModel>> getByProject(ProjectModel project);
}
