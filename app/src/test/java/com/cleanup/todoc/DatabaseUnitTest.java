package com.cleanup.todoc;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = Config.OLDEST_SDK)

    public class DatabaseUnitTest {
        private TaskDao taskDao;

    // DATA SET FOR TEST
    private static final Task TASK_DEMO_1 = new Task(1, "Tache de test aaa", System.currentTimeMillis());
    private static final Task TASK_DEMO_2 = new Task(2, "Tache de test bbb", System.currentTimeMillis()+100000000);
    private static final Task TASK_DEMO_3 = new Task(3, "Tache de test ccc", System.currentTimeMillis()+200000000);
    private static final Task TASK_DEMO_4 = new Task(4, "Tache de test ddd", System.currentTimeMillis()+300000000);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    // FOR DATA
    private TodocDatabase db;

    // Populates the test database with the projects
    private static RoomDatabase.Callback prepopulateDatabase() {
        return new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues projectContentValues = new ContentValues();

                projectContentValues.put("id", 1);
                projectContentValues.put("name", "Projet Tartampion");
                projectContentValues.put("color", 0xFFEADAD1);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);

                projectContentValues.put("id", 2);
                projectContentValues.put("name", "Projet Lucidia");
                projectContentValues.put("color", 0xFFB4CDBA);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);

                projectContentValues.put("id", 3);
                projectContentValues.put("name", "Projet Circus");
                projectContentValues.put("color", 0xFFA3CED2);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);
            }
        };
    }

    @Before
    public void initDb() {
        this.db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .addCallback(prepopulateDatabase())
                .build();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void getProjects() {
        List<Project> projectList = this.db.projectDao().getAllProjects();
           assertEquals(3, projectList.size());
    }

    @Test
    public void getTasksWhenNoTaskCreated() {
        // TEST that the tasklist is empty when no task created
        List<Task> taskList = this.db.taskDao().getAllTask();
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void insertTasks() {
        // Add the demo tasks

        this.db.taskDao().insertTask(TASK_DEMO_1);
        this.db.taskDao().insertTask(TASK_DEMO_2);
        this.db.taskDao().insertTask(TASK_DEMO_3);

        //TEST  that the added task is in the database
        List<Task> taskList = this.db.taskDao().getAllTask();
        assertEquals(3, taskList.size());
        assertEquals(taskList.get(0).getName(), TASK_DEMO_1.getName());
        assertEquals(taskList.get(1).getName(), TASK_DEMO_2.getName());
        assertEquals(taskList.get(2).getName(), TASK_DEMO_3.getName());
    }

    @Test
    public void insertTaskWithWrongProjectId() {
        // Try adding the demo tasks with a projectId that belongs to no project in the database. The insert must fail
        try {
            this.db.taskDao().insertTask(TASK_DEMO_4);
        } catch (Exception e) {
            // TEST that the added task is NOT in the database
            List<Task> taskList = this.db.taskDao().getAllTask();
            assertTrue(taskList.isEmpty());
            assertNotNull(e);
        }
    }

    @Test
    public void insertAndDeleteTask() {
        // Add a task then delete it.
        this.db.taskDao().insertTask(TASK_DEMO_1);

        //TEST  that the added task is in the database
        List<Task> taskList = this.db.taskDao().getAllTask();
        assertEquals(1, taskList.size());

        // Delete the task
        Task taskAdded = this.db.taskDao().getAllTask().get(0);
        this.db.taskDao().deleteTask(taskAdded.getId());

        //TEST  that the added task is deleted
        taskList = this.db.taskDao().getAllTask();
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void insertTasksThenOrderAZ() {
        // Add the demo tasks
        this.db.taskDao().insertTask(TASK_DEMO_1); // aaa
        this.db.taskDao().insertTask(TASK_DEMO_2); // bbb
        this.db.taskDao().insertTask(TASK_DEMO_3); // ccc

        //TEST  that the added task is in the database
        List<Task> taskList = this.db.taskDao().getTasksAlphabeticalAZ();
        assertEquals(3, taskList.size());
        assertEquals(taskList.get(0).getName(), TASK_DEMO_1.getName());
        assertEquals(taskList.get(1).getName(), TASK_DEMO_2.getName());
        assertEquals(taskList.get(2).getName(), TASK_DEMO_3.getName());
    }

    @Test
    public void insertTasksThenOrderZA() {
        // Add the demo tasks
        this.db.taskDao().insertTask(TASK_DEMO_1); // aaa
        this.db.taskDao().insertTask(TASK_DEMO_2); // bbb
        this.db.taskDao().insertTask(TASK_DEMO_3); // ccc

        //TEST
        List<Task> taskList = this.db.taskDao().getTasksAlphabeticalZA();
        assertEquals(3, taskList.size());
        assertEquals(taskList.get(0).getName(), TASK_DEMO_3.getName());
        assertEquals(taskList.get(1).getName(), TASK_DEMO_2.getName());
        assertEquals(taskList.get(2).getName(), TASK_DEMO_1.getName());
    }

    @Test
    public void insertTasksThenOrderNewToOld()  {
        // Add the demo tasks
        this.db.taskDao().insertTask(TASK_DEMO_1);
        this.db.taskDao().insertTask(TASK_DEMO_2);
        this.db.taskDao().insertTask(TASK_DEMO_3);

        //TEST
        List<Task> taskList = this.db.taskDao().getTasksNewToOld();
        assertEquals(3, taskList.size());
        assertEquals(taskList.get(0).getName(), TASK_DEMO_3.getName());
        assertEquals(taskList.get(1).getName(), TASK_DEMO_2.getName());
        assertEquals(taskList.get(2).getName(), TASK_DEMO_1.getName());
    }

    @Test
    public void insertTasksThenOrderOldtoNew() {
        // Add the demo tasks
        this.db.taskDao().insertTask(TASK_DEMO_1);
        this.db.taskDao().insertTask(TASK_DEMO_2);
        this.db.taskDao().insertTask(TASK_DEMO_3);

        //TEST  that the added task is in the database
        List<Task> taskList = this.db.taskDao().getTasksOldToNew();
        assertEquals(3, taskList.size());
        assertEquals(taskList.get(0).getName(), TASK_DEMO_1.getName());
        assertEquals(taskList.get(1).getName(), TASK_DEMO_2.getName());
        assertEquals(taskList.get(2).getName(), TASK_DEMO_3.getName());
    }


}
