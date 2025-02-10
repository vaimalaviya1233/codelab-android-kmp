package com.example.fruitties.kmptutorial.android.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.fruitties.kmptutorial.android.model.CartItem
import com.example.fruitties.kmptutorial.android.model.CartItemWithFruittie
import com.example.fruitties.kmptutorial.android.model.Fruittie
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)

    @Transaction
    @Query("SELECT * FROM cartitem")
    fun getAll(): Flow<List<CartItemWithFruittie>>

    @Query("SELECT * FROM cartitem WHERE id = :id")
    suspend fun findById(id: Long): CartItem?

    @Transaction
    suspend fun insertOrIncreaseCount(fruittie: Fruittie) {
        val cartItemInDb = findById(fruittie.id)
        if (cartItemInDb == null) {
            // New item
            insert(CartItem(fruittie.id))
        } else {
            // Existing item
            update(cartItemInDb.copy(count = cartItemInDb.count + 1))
        }
    }
}