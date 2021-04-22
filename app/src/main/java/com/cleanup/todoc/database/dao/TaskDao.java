package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    List<Task> getTasks();

    @Insert
    void insertTask(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    void deleteTask(long taskId);

    @Query("SELECT * FROM Task ORDER BY name ASC")
    List<Task> getTasksAlphabeticalAZ();

    @Query("SELECT * FROM Task ORDER BY name DESC")
    List<Task> getTasksAlphabeticalZA();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
    List<Task> getTasksNewToOld();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp ASC")
    List<Task> getTasksOldToNew();


}
