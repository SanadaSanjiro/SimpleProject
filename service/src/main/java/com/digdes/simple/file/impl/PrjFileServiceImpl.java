package com.digdes.simple.file.impl;

import com.digdes.simple.FileManager;
import com.digdes.simple.file.*;
import com.digdes.simple.project.ProjectDAO;
import com.digdes.simple.project.ProjectModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PrjFileServiceImpl implements PrjFileService {

    @Autowired
    private final ProjectDAO projectDAO;
    @Autowired
    private final PrjFileDAO prjFileDAO;

    @Transactional(rollbackFor = {IOException.class})
    @Override
    public PrjFileDTO addPrjFile(String code, MultipartFile resource) throws IOException {
        if (code == null || resource == null || projectDAO.getByCode(code) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ProjectModel projectModel = projectDAO.getByCode(code);
        String name = resource.getOriginalFilename();

        PrjFileKey key = new PrjFileKey();
        key.setName(name);
        key.setPrjcode(projectModel.getCode());

        PrjFileModel prjFileModel = new PrjFileModel();
        prjFileModel.setProject(projectModel);
        prjFileModel.setKey(key);

        prjFileModel = prjFileDAO.create(prjFileModel);

        Set<PrjFileModel> set = projectModel.getFiles();
        set.add(prjFileModel);
        projectModel.setFiles(set);

        FileManager fileManager = new FileManager();
        String path = "\\projects\\" + projectModel.getCode() + "\\";
        fileManager.upload(resource.getBytes(), name, path);

        projectModel = projectDAO.update(projectModel);
        return PrjFileMapper.map(prjFileModel);
    }

    @Override
    public List<PrjFileDTO> getByPrjCode(String code) throws IOException {
        if (code == null || projectDAO.getByCode(code) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ProjectModel model = projectDAO.getByCode(code);
        return prjFileDAO.getByProject(model)
                .stream()
                .map(m-> PrjFileMapper.map(m))
                .toList();
    }

    @Override
    public PrjFileModel findPrjFileByKey(String code, String name) {
        if (code == null || projectDAO.getByCode(code) == null || name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        PrjFileKey key = new PrjFileKey();
        key.setPrjcode(code);
        key.setName(name);
        return prjFileDAO.getByKey(key);
    }

    @Override
    public Resource downloadPrjFile(PrjFileKey key) throws IOException {
        FileManager fileManager = new FileManager();
        String name = key.getName();
        String path = "\\projects\\" + key.getPrjcode() +"\\" + name;
        return fileManager.download(path);
    }
}
