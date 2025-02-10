package com.example.fruitties.kmptutorial.android.database

import android.content.Context
import androidx.room.Room

fun getDatabaseBuilder(context: Context) = DatabaseBuilder {
    val dbFile = context.getDatabasePath("fruits.db")
    Room.databaseBuilder<AppDatabase>(context, dbFile.absolutePath)
}