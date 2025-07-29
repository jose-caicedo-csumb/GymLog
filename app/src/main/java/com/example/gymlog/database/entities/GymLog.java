/**
 * Author: Jose Caicedo (transcribed, original code by Dr. Clinkenbeard, PhD)
 * Date: 7/28/2025
 * <p>
 * Explanation: This entity represents a log entry for the GymLog app.
 * It stores exercise details like name, weight, reps, and date, along with
 * the associated user ID. I didn’t create the code logic (it was provided by
 * Dr. Clinkenbeard) but added these comments to help make the purpose and
 * flow of the class easier to follow.
 */

package com.example.gymlog.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gymlog.database.GymLogDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a single gym log record stored in the database.
 * Each log keeps track of the exercise performed, weight used,
 * number of reps, date it was logged, and which user it belongs to.
 */
@Entity(tableName = GymLogDatabase.GYM_LOG_TABLE)
public class GymLog {

    /**
     * Primary key for the log entry. Automatically generated.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Name of the exercise (e.g., "Bench Press").
     */
    private String exercise;

    /**
     * Weight used during the exercise, in pounds or kilograms.
     */
    private double weight;

    /**
     * Number of repetitions performed.
     */
    private int reps;

    /**
     * Timestamp for when this log entry was created.
     */
    private LocalDateTime date;

    /**
     * Identifier for the user who owns this log.
     */
    private int userId;

    /**
     * Creates a new gym log entry. The date is automatically set to the
     * current time.
     *
     * @param exercise The name of the exercise performed.
     * @param weight   The amount of weight used.
     * @param reps     The number of repetitions performed.
     * @param userId   The ID of the user associated with this log.
     */
    public GymLog(String exercise, double weight, int reps, int userId) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.userId = userId;
        date = LocalDateTime.now();
    }

    /**
     * Returns a string with all the log details for display.
     *
     * @return A formatted string with the exercise, weight, reps, and date.
     */
    @NonNull
    @Override
    public String toString() {
        return exercise + '\n' +
                "weight: " + weight + '\n' +
                "reps: " + reps + '\n' +
                "date: " + date.toString() + '\n' +
                "=-=-=-=-=-=-=\n";
    }

    /**
     * Compares two GymLog objects for equality by checking all fields.
     *
     * @param o The object to compare with.
     * @return true if the two logs are identical; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GymLog gymLog = (GymLog) o;
        return id == gymLog.id &&
                Double.compare(weight, gymLog.weight) == 0 &&
                reps == gymLog.reps &&
                userId == gymLog.userId &&
                Objects.equals(exercise, gymLog.exercise) &&
                Objects.equals(date, gymLog.date);
    }

    /**
     * Generates a unique hash code for this log entry based on all fields.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, exercise, weight, reps, date, userId);
    }

    // Getters and setters for accessing and modifying the log fields.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
