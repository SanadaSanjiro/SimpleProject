package com.digdes.simple.file.impl;

import com.digdes.simple.FileManager;
import com.digdes.simple.file.*;
import com.digdes.simple.task.TaskDAO;
import com.digdes.simple.task.TaskModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TskFileServiceImpl implements TskFileService {
    @Autowired
    private final TaskDAO taskDAO;
    @Autowired
    private final TskFileDAO tskFileDAO;

    @Override
    public TskFileDTO addTskFile(Long id, MultipartFile resource) throws IOException {
        if (id == null || resource == null || taskDAO.getById(id) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        TaskModel taskModel = taskDAO.getById(id);
        String name = resource.getOriginalFilename();

        TskFileKey key = new TskFileKey();
        key.setName(name);
        key.setTaskid(taskModel.getId());

        TskFileModel tskFileModel = new TskFileModel();
        tskFileModel.setTaskModel(taskModel);
        tskFileModel.setKey(key);

        tskFileModel = tskFileDAO.create(tskFileModel);

        Set<TskFileModel> set = taskModel.getFiles();
        set.add(tskFileModel);
        taskModel.setFiles(set);

        FileManager fileManager = new FileManager();
        String path = "\\tasks\\" + taskModel.getId() + "\\";
        fileManager.upload(resource.getBytes(), name, path);

        taskModel = taskDAO.update(taskModel);
        return TskFileMapper.map(tskFileModel);
    }

    @Override
    public List<TskFileDTO> getByTskId(Long id) throws IOException {
        if (id == null || taskDAO.getById(id) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        TaskModel model = taskDAO.getById(id);
        return tskFileDAO.getByTask(model)
                .stream()
                .map(m -> TskFileMapper.map(m))
                .toList();
    }

    @Override
    public TskFileModel findTskFileByKey(Long id, String name) {
        if (id == null || taskDAO.getById(id) == null || name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        TskFileKey key = new TskFileKey();
        key.setTaskid(id);
        key.setName(name);
        return tskFileDAO.getByKey(key);
    }

    @Override
    public Resource downloadTskFile(TskFileKey key) throws IOException {
        FileManager fileManager = new FileManager();
        String name = key.getName();
        String path = "\\tasks\\" + key.getTaskid() +"\\" + name;
        return fileManager.download(path);
    }
}
