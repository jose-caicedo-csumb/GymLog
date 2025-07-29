/**
 * Author: Jose Caicedo (transcribed, original code by Dr. Clinkenbeard, PhD)
 * Date: 7/28/2025
 * <p>
 * Explanation: This class represents a User entity for the GymLog app.
 * Each user has a unique ID, username, password, and a flag indicating if
 * they have admin rights. The logic was originally provided by Dr. Clinkenbeard,
 * and I (Jose Caicedo) transcribed it and added comments so it’s easier to read.
 */

package com.example.gymlog.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gymlog.database.GymLogDatabase;

import java.util.Objects;

/**
 * Represents a user account stored in the database.
 * Each user has a unique auto-generated ID, a username, password,
 * and an admin status flag.
 */
@Entity(tableName = GymLogDatabase.USER_TABLE)
public class User {

    /**
     * Unique primary key for the user. Automatically generated.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Username used to log into the app.
     */
    private String username;

    /**
     * User's password. (Stored as plain text here but should ideally be hashed.)
     */
    private String password;

    /**
     * Flag to indicate if this user has administrator rights.
     * Defaults to false.
     */
    private boolean isAdmin = false;

    /**
     * Creates a new user with the specified username and password.
     * Admin status is false by default.
     *
     * @param username The username for the new user.
     * @param password The password for the new user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
    }

    /**
     * Compares two User objects for equality by checking all fields.
     *
     * @param o The object to compare with.
     * @return true if all fields match; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                isAdmin == user.isAdmin &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    /**
     * Generates a hash code based on the user's ID, username, password, and admin flag.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isAdmin);
    }

    // Standard getters and setters for each field.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
