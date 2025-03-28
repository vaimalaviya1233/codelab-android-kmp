package com.example.fruitties.kmptutorial.android.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fruitties.kmptutorial.android.model.Fruittie
import kotlinx.coroutines.flow.Flow

@Dao
interface FruittieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fruitties: List<Fruittie>): List<Long>

    @Query("SELECT * FROM Fruittie")
    fun getAll(): Flow<List<Fruittie>>

    @Query("SELECT COUNT(*) as count FROM Fruittie")
    suspend fun count(): Int
}