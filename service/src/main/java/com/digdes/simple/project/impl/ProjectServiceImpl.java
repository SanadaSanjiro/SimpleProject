package com.digdes.simple.project.impl;

import com.digdes.simple.FileManager;
import com.digdes.simple.file.FileKey;
import com.digdes.simple.file.FileModel;
import com.digdes.simple.file.FileRepository;
import com.digdes.simple.project.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private final ProjectDAO projectDAO;
    @Autowired
    private final FileRepository fileRepository;

    @Override
    public ProjectDTO getByCode(String code) {
        if (code==null) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}
        ProjectModel model = projectDAO.getByCode(code);
        if (model==null) {throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
        return ProjectDTOMapper.map(model);
    }

    @Override
    public ProjectDTO create(ProjectCrtDTO dto) {
        if (dto == null || dto.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (projectDAO.getByCode(dto.getCode())!=null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ProjectModel model = ProjectCrtMapper.map(dto);
        model.setStatus(ProjectStatus.DRAFT);
        model = projectDAO.create(model);
        return ProjectDTOMapper.map(model);
    }

    @Override
    public ProjectDTO update(ProjectCrtDTO dto) {
        if (dto == null || dto.getName() == null || dto.getCode() ==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if ((projectDAO.getByCode(dto.getCode())==null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ProjectModel model = ProjectCrtMapper.map(dto);
        ProjectStatus status = projectDAO.getByCode(dto.getCode()).getStatus();
        model.setStatus(status);
        model = projectDAO.update(model);
        return ProjectDTOMapper.map(model);
    }

    @Override
    public ProjectDTO changeStatus(String code) {
        if (code == null || projectDAO.getByCode(code) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ProjectModel model = projectDAO.getByCode(code);
        ProjectStatus status = model.getStatus();
        switch (status) {
            case DRAFT: {
                status = ProjectStatus.DEVELOPING;
                break;
            }
            case DEVELOPING: {
                status = ProjectStatus.TESTING;
                break;
            }
            case TESTING: {
                status = ProjectStatus.COMPLETE;
                break;
            }
        }
        model.setStatus(status);
        model = projectDAO.changeStatus(model);
        return ProjectDTOMapper.map(model);
    }

    @Override
    public List<ProjectDTO> getFiltered(ProjectSrchDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<ProjectDTO> dtos = projectDAO.getFiltered(dto)
                .stream()
                .map(m-> ProjectDTOMapper.map(m))
                .toList();
        return dtos;
    }

    @Override
    public List<ProjectDTO> getAll() {
        List<ProjectDTO> dtos = projectDAO.getAll()
                .stream()
                .map(m-> ProjectDTOMapper.map(m))
                .toList();
        return dtos;
    }

    @Transactional(rollbackFor = {IOException.class})
    @Override
    public ProjectDTO addFile(String code,MultipartFile resource) throws IOException {
        if (code == null || resource == null || projectDAO.getByCode(code) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ProjectModel model = projectDAO.getByCode(code);
        String name = resource.getOriginalFilename();

        FileKey key = new FileKey();
        key.setName(name);
        key.setPrjcode(model.getCode());

        FileModel file = new FileModel();
        file.setProject(model);
        file.setKey(key);

        Set<FileModel> set = model.getFiles();
        set.add(file);
        model.setFiles(set);
        System.out.println(model);
        System.out.println(model.getFiles());

        FileManager fileManager = new FileManager();
        fileManager.upload(resource.getBytes(), name);

        System.out.println(fileRepository.save(file));

        model = projectDAO.update(model);
        return ProjectDTOMapper.map(model);
    }
}
