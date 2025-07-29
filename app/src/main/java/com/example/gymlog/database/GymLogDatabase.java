/**
 * Author: Jose Caicedo (transcribed, original code by Dr. Clinkenbeard, PhD)
 * Date: 7/28/2025
 *
 * Explanation: This class sets up and manages the Room database for the GymLog app.
 * It defines the database schema, tables, DAOs, and initializes default values when
 * the database is first created. The core logic is provided by Dr. Clinkenbeard,
 * and I (Jose Caicedo) transcribed and added comments to make it easier to follow.
 */

package com.example.gymlog.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.gymlog.database.entities.GymLog;
import com.example.gymlog.MainActivity;
import com.example.gymlog.database.entities.User;
import com.example.gymlog.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Room database for the GymLog app.
 * It holds two tables: one for user data and one for gym log entries.
 * It also includes DAOs for database interactions and seeds the database with
 * default users when created.
 */
@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {GymLog.class, User.class}, version = 5, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {

    /**
     * Constant for the name of the user table.
     */
    public static final String USER_TABLE = "usertable";

    /**
     * Name of the database file.
     */
    private static final String DATABASE_NAME = "GymLogDatabase";

    /**
     * Constant for the gym log table name.
     */
    public static final String GYM_LOG_TABLE = "gymLogTable";

    /**
     * Singleton instance of the database to prevent multiple open connections.
     */
    private static volatile GymLogDatabase INSTANCE;

    /**
     * Number of threads in the pool for database operations.
     */
    private static final int NUMBER_OF_THREADS = 4;

    /**
     * Executor service for running database operations asynchronously.
     */
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Retrieves the singleton database instance, creating it if it doesn’t exist.
     * Configured with fallback migration and default data population.
     *
     * @param context Application context.
     * @return The GymLogDatabase instance.
     */
    static GymLogDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GymLogDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    GymLogDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Callback used to populate the database with default data (admin and test users)
     * when the database is first created.
     */
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();

                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    /**
     * Provides access to the GymLog data access object (DAO).
     *
     * @return GymLogDAO instance.
     */
    public abstract GymLogDAO gymLogDAO();

    /**
     * Provides access to the User data access object (DAO).
     *
     * @return UserDAO instance.
     */
    public abstract UserDAO userDAO();
}
