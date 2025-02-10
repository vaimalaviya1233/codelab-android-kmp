package com.example.fruitties.kmptutorial.shared

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fruitties.kmptutorial.android.database.AppDatabase
import com.example.fruitties.kmptutorial.android.database.DatabaseBuilder
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun databaseBuilder() = DatabaseBuilder {
    val dbFilePath = documentDirectory() + "/fruits.db"
    Room.databaseBuilder<AppDatabase>(name = dbFilePath)
}

fun databaseBuilderX(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/fruits.db"
    return Room.databaseBuilder<AppDatabase>(name = dbFilePath)
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )

    return documentDirectory?.path ?: error("Problem loading the document directory.")
}
