package com.cleanup.todoc.service;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private final ProjectDao projectDao;

    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    // --- GET PROJECT LIST---
    public List<Project> getAllProjects() {
        return this.projectDao.getAllProjects();
    }

   }
