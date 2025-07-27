# Overview and plan
The goal of this project is to work through all the steps necessary to create an app
that uses the Room database wrapper for persistence and that allows users to log in.
If I am feeling brave I will add in a recycler view too.

## Setup
1. Create a gitignore file
2. Create a branch
3. make the intial layout

## Basic functions
1. Add [viewBindings] (https://developer.android.com/topic/libraries/view-binding)
2. Wire up the button
3. Read information from the display
4. Log our info
5. Test the display

## Add the Database
1. Add the room dependencies
    * [Android Room Dependencies](https://developer.android.com/jetpack/androidx/releases/room)
2. Create the POJO entity objects
    * for now just a GymLog
3. Create the database interface
4. Create the GymLog DAO
5. make the repository
6. Use the Room Repooo to write/read logs.

## Add users
1. Create a User POJO
2. Create a User DAO
3. Up[date the DB repo to allow user operations
4. Add a login screen
5. Add a menu to facilitate loggin in and out
6. Add logout function

## Add a Recycler
1. Update the GymLog DAO to return liveData
2. Create the viewHolder
3. Create the adapter
4. Update MainActivity to have a recycler view
5. profit?