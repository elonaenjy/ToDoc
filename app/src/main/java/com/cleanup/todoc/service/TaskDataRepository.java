package com.cleanup.todoc.service;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDataRepository {
    private final TaskDao taskDao;
    //to isalte Dao of ViewModel
    public TaskDataRepository(TaskDao taskDao) {this.taskDao = taskDao;}

    public List<Task> getTasks() {return taskDao.getTasks(); }

    public List<Task> getTasksAZ() { return taskDao.getTasksAlphabeticalAZ(); }

    public List<Task> getTasksZA() {
        return taskDao.getTasksAlphabeticalZA();
    }

    public List<Task> getTasksNewOld() {
        return taskDao.getTasksNewToOld();
    }

    public List<Task> getTasksOldNew() {
        return taskDao.getTasksOldToNew();
    }

    public void addTask(Task task) {
        taskDao.insertTask(task);
    }

    public void deleteTask(long id) {
        taskDao.deleteTask(id);
    }

}

