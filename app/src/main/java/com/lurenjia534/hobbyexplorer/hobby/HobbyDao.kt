package com.lurenjia534.hobbyexplorer.hobby

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HobbyDao {
    @Insert
    suspend fun insert(hobby: Hobby)

    @Query("SELECT * FROM hobbies")
    fun getAllHobbies(): Flow<List<Hobby>>
}
