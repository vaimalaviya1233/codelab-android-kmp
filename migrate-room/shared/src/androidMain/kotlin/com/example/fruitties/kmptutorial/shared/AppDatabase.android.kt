package com.example.fruitties.kmptutorial.shared

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.fruitties.kmptutorial.android.database.AppDatabase

fun appDatabase(context: Context) = Room
    .databaseBuilder(context, AppDatabase::class.java, "sharedfruits.db")
    .setDriver(BundledSQLiteDriver())
    .build()