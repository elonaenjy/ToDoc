package com.cleanup.todoc.service;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;
public class TaskRepository {
    private static TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    // --- READ ---

    public static List<Task> getTasks() {return taskDao.getAllTask(); }

    public List<Task> getTasksAZ() {
        return this.taskDao.getTasksAlphabeticalAZ();
    }

    public List<Task> getTasksZA() {
        return this.taskDao.getTasksAlphabeticalZA();
    }

    public List<Task> getTasksNewOld() {
        return this.taskDao.getTasksNewToOld();
    }

    public List<Task> getTasksOldNew() {
        return this.taskDao.getTasksOldToNew();
    }

    // --- CREATE ---

    public void addTask(Task task) {
        taskDao.insertTask(task);
    }

    // --- DELETE ---
    public void deleteTask(long id) {
        taskDao.deleteTask(id);
    }

}