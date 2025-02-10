/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.fruitties.android.database

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.fruitties.kmptutorial.android.database.AppDatabase
import com.example.fruitties.kmptutorial.android.database.CartDao
import com.example.fruitties.kmptutorial.android.database.FruittieDao
import com.example.fruitties.kmptutorial.android.model.CartItem
import com.example.fruitties.kmptutorial.android.model.Fruittie
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class CartDaoTest {

    companion object {
        private const val TEST_FRUITTIE_ID = 123L
    }

    private lateinit var db: AppDatabase
    private lateinit var cartDao: CartDao
    private lateinit var fruittieDao: FruittieDao
    private val testFruittie =
        Fruittie(TEST_FRUITTIE_ID, "Test Fruittie", "AndroidTest Fruittie", "0")

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        cartDao = db.cartDao()
        fruittieDao = db.fruittieDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun getAll_noItems_returnsEmptyList() = runTest {
        assertEquals(0, cartDao.getAll().first().size)
    }

    /**
     * Adding a cart item without having corresponding Fruittie in the DB would cause exception
     */
    @Test
    fun insert_foreignKeyConstraintViolation_throwsException() = runTest {
        assertThrows(SQLiteConstraintException::class.java) {
            runBlocking {
                cartDao.insert(CartItem(1))
            }
        }
    }

    @Test
    fun getAll_itemsExist_returnsListOfItems() = runTest {
        fruittieDao.insert(listOf(testFruittie))
        cartDao.insert(CartItem(TEST_FRUITTIE_ID))
        assertEquals(1, cartDao.getAll().first().size)
        assertEquals(TEST_FRUITTIE_ID, cartDao.getAll().first()[0].fruittie.id)
    }

    @Test
    fun findById_existingId_returnsMatchingItem() = runTest {
        fruittieDao.insert(listOf(testFruittie))
        cartDao.insert(CartItem(TEST_FRUITTIE_ID))
        assertEquals(TEST_FRUITTIE_ID, cartDao.findById(TEST_FRUITTIE_ID)?.id)
    }
}
