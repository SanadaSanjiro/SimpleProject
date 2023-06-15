package com.digdes.simple.project;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProjectService {
    ProjectDTO getByCode(String code);

    ProjectDTO create(ProjectCrtDTO dto);

    ProjectDTO update(ProjectCrtDTO dto);

    ProjectDTO changeStatus(String code);

    List<ProjectDTO> getFiltered(ProjectSrchDTO dto);

    List<ProjectDTO> getAll();
}
