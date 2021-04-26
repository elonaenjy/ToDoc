package com.cleanup.todoc.service;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository  {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    // --- GET PROJECT LIST---
    public List<Project> getAllProjects() {
        return this.projectDao.getAllProjects();
    }

   }
