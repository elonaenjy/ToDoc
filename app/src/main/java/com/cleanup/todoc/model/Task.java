package com.cleanup.todoc.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Project.class,
        parentColumns = "id",
        childColumns = "projectId"))

public class Task {
    @PrimaryKey(autoGenerate = true)
    private long id;
    /**
     * The unique identifier of the project associated to the task
     */
    private long projectId;
    /**
     * The name of the task
     */
    private String name;
    /**
     * The timestamp when the task has been created
     */
    private long creationTimestamp;

    public Task() {
    }

    public Task(long projectId, String name, long creationTimestamp) {
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
