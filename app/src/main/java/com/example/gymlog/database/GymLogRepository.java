/**
 * Author: Jose Caicedo (transcribed, original code by Dr. Clinkenbeard, PhD)
 * Date: 7/28/2025
 *
 * Explanation: This repository class manages database operations for the GymLog app.
 * It acts as a bridge between the DAOs and the rest of the application, handling
 * GymLog and User data queries, inserts, and LiveData updates. The logic was
 * originally provided by Dr. Clinkenbeard. I (Jose Caicedo) transcribed it and
 * added documentation so others can understand its purpose and flow more easily.
 */

package com.example.gymlog.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.gymlog.database.entities.GymLog;
import com.example.gymlog.MainActivity;
import com.example.gymlog.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Repository for managing GymLog and User data.
 * Handles background threads for database operations and provides both synchronous
 * and LiveData-based access to information.
 */
public class GymLogRepository {

    /**
     * DAO for gym log entries.
     */
    private final GymLogDAO gymLogDAO;

    /**
     * DAO for user data.
     */
    private final UserDAO userDAO;

    /**
     * Cached list of all gym logs.
     */
    private ArrayList<GymLog> allLogs;

    /**
     * Singleton repository instance.
     */
    private static GymLogRepository repository;

    /**
     * Private constructor to enforce singleton pattern.
     * Initializes DAOs and preloads all gym logs.
     *
     * @param application The Application context used for building the database.
     */
    private GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<GymLog>) this.gymLogDAO.getAllRecords();
    }

    /**
     * Gets the singleton repository instance, creating it in a background thread
     * if it doesn’t exist yet.
     *
     * @param application The Application context.
     * @return The GymLogRepository instance.
     */
    public static GymLogRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<GymLogRepository> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<GymLogRepository>() {
                    @Override
                    public GymLogRepository call() {
                        return new GymLogRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    /**
     * Retrieves all gym logs synchronously using a background thread.
     *
     * @return An ArrayList of all GymLog records, or null if retrieval fails.
     */
    public ArrayList<GymLog> getAllLogs() {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() {
                        return (ArrayList<GymLog>) gymLogDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all GymLogs in the repository.");
        }
        return null;
    }

    /**
     * Inserts a new GymLog record asynchronously.
     *
     * @param gymLog The GymLog object to insert.
     */
    public void insertGymLog(GymLog gymLog) {
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
            gymLogDAO.insert(gymLog);
        });
    }

    /**
     * Inserts one or more User records asynchronously.
     *
     * @param user The User objects to insert.
     */
    public void insertUser(User... user) {
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    /**
     * Retrieves a User by username as LiveData.
     *
     * @param username The username to look up.
     * @return A LiveData object containing the User.
     */
    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    /**
     * Retrieves a User by user ID as LiveData.
     *
     * @param userId The user ID to look up.
     * @return A LiveData object containing the User.
     */
    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    /**
     * Retrieves all GymLogs for a specific user as LiveData.
     *
     * @param loggedInUserId The ID of the user whose logs to fetch.
     * @return A LiveData object containing the list of GymLogs.
     */
    public LiveData<List<GymLog>> getAllLogsByUserIdLiveData(int loggedInUserId) {
        return gymLogDAO.getRecordsetUserIdLiveData(loggedInUserId);
    }

    /**
     * Retrieves all GymLogs for a specific user synchronously.
     * This method is marked as deprecated in favor of the LiveData-based version.
     *
     * @param loggedInUserId The ID of the user whose logs to fetch.
     * @return An ArrayList of GymLogs, or null if retrieval fails.
     */
    @Deprecated
    public ArrayList<GymLog> getAllLogsByUserId(int loggedInUserId) {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() {
                        return (ArrayList<GymLog>) gymLogDAO.getRecordsetUserId(loggedInUserId);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all GymLogs in the repository");
        }
        return null;
    }
}
