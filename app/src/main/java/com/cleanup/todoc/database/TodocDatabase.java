package com.cleanup.todoc.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 2)
public abstract class TodocDatabase extends RoomDatabase {

    private static volatile TodocDatabase INSTANCE;

    // --- DAO ---
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    private static final int NUMBER_OF_THREADS = 5;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // --- INSTANCE ---
    public static final TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "ToDoc.db")
                            .allowMainThreadQueries()
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static Callback prepopulateDatabase() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues projectContentValues = new ContentValues();

                projectContentValues.put("id", 1L);
                projectContentValues.put("name", "Projet Tartampion");
                projectContentValues.put("color", 0xFFEADAD1);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);

                projectContentValues.put("id", 2L);
                projectContentValues.put("name", "Projet Lucidia");
                projectContentValues.put("color", 0xFFB4CDBA);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);

                projectContentValues.put("id", 3L);
                projectContentValues.put("name", "Projet Circus");
                projectContentValues.put("color", 0xFFA3CED2);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);
            }
        };
    }

}

