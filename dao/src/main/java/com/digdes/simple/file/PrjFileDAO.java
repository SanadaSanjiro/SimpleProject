package com.digdes.simple.file;

import com.digdes.simple.project.ProjectModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PrjFileDAO {
    @Autowired
    PrjFileRepository fileRepository;

    public PrjFileModel getByKey(PrjFileKey key) {
        Optional<PrjFileModel> optional = fileRepository.findById(key);
        if (optional.isPresent()) { return optional.get(); }
        else return null;
    }

    public PrjFileModel create(PrjFileModel prjFileModel) {
        return fileRepository.save(prjFileModel);
    }

    public List<PrjFileModel> getByProject(ProjectModel model) {
        Optional<List<PrjFileModel>> optional = fileRepository.getByProject(model);
        if (optional.isPresent()) {
            return optional.get();
        } else return null;
    }
}
