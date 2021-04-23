package com.cleanup.todoc.repositories;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private static TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public static List<Task> getTasks() {
        return taskDao.getTasks();
    }

    public static List<Task> getTasksAZ() { return taskDao.getTasksAlphabeticalAZ(); }

    public static List<Task> getTasksZA() {
        return taskDao.getTasksAlphabeticalZA();
    }

    public static List<Task> getTasksNewOld() {
        return taskDao.getTasksNewToOld();
    }

    public static List<Task> getTasksOldNew() {
        return taskDao.getTasksOldToNew();
    }

    public static void addTask(Task task) {
        taskDao.insertTask(task);
    }

    public static void deleteTask(long id) {
        taskDao.deleteTask(id);
    }

}

