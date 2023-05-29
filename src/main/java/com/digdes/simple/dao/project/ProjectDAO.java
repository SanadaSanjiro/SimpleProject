package com.digdes.simple.dao.project;

import com.digdes.simple.model.project.ProjectModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectDAO {

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectModel create(ProjectModel projectModel) {
        try {
            return projectRepository.save(projectModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProjectModel update (ProjectModel projectModel) {
        try {
            return projectRepository.save(projectModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProjectModel changeStatus (ProjectModel projectModel) {
        try {
            return projectRepository.save(projectModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProjectModel> getFilterd() {
        List<ProjectModel> list = new ArrayList<>();
        return list;
    }
}