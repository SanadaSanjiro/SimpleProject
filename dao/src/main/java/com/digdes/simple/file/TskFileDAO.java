package com.digdes.simple.file;

import com.digdes.simple.task.TaskModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TskFileDAO {
    @Autowired
    TskFileRepository repository;

    public TskFileModel getByKey(TskFileKey key) {
        Optional<TskFileModel> optional = repository.findById(key);
        if (optional.isPresent()) { return optional.get(); }
        else return null;
    }

    public TskFileModel create(TskFileModel tskFileModel) {
        return repository.save(tskFileModel);
    }

    public List<TskFileModel> getByTask(TaskModel model) {
        Optional<List<TskFileModel>> optional = repository.getByTaskModel(model);
        if (optional.isPresent()) {
            return optional.get();
        } else return null;
    }
}
