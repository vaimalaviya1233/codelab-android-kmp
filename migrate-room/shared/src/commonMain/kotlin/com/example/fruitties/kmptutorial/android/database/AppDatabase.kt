package com.example.fruitties.kmptutorial.android.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fruitties.kmptutorial.android.model.CartItem
import com.example.fruitties.kmptutorial.android.model.Fruittie

import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

@Database(
    entities = [Fruittie::class, CartItem::class],
    version = 1,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fruittieDao(): FruittieDao
    abstract fun cartDao(): CartDao
}
