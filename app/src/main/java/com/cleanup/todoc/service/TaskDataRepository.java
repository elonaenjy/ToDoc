package com.cleanup.todoc.service;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {
    private final TaskDao mTaskDao;

    public TaskDataRepository(TaskDao mTaskDao) {
        this.mTaskDao = mTaskDao;
    }

//    public TaskDataRepository(Application application){
//        TodocDatabase dataBase = TodocDatabase.getInstance(application);
//        mTaskDao = dataBase.taskDao();
//    }

    public List<Task> getAllTask() { return mTaskDao.getTasks(); }

    public List<Task> getTasksAZ() { return mTaskDao.getTasksAlphabeticalAZ(); }

    public List<Task> getTasksZA() { return mTaskDao.getTasksAlphabeticalZA(); }

    public List<Task> getTasksNewOld() {return mTaskDao.getTasksNewToOld(); }

    public List<Task> getTasksOldNew() { return mTaskDao.getTasksOldToNew(); }

    public void deleteTask(long id) { mTaskDao.deleteTask(id); }
    public void addTask(final Task task){
        TodocDatabase.databaseWriteExecutor.execute(() -> mTaskDao.insertTask(task));
    }

    public void delete(final Task task){
        TodocDatabase.databaseWriteExecutor.execute(() -> mTaskDao.deleteTask(task.getId()));
    }

}

