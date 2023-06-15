package com.digdes.simple.file;

import com.digdes.simple.task.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TskFileRepository extends JpaRepository<TskFileModel, TskFileKey> {
    Optional<List<TskFileModel>> getByTaskModel(TaskModel task);
}
