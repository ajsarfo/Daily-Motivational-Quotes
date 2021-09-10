package com.sarftec.dailymotivationalquotes.data.database.dao.author

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarftec.dailymotivationalquotes.data.database.AUTHOR_TABLE
import com.sarftec.dailymotivationalquotes.data.database.entity.author.AppAuthorEntity

@Dao
interface AppAuthorEntityDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(author: AppAuthorEntity) : Long

    @Query("select * from $AUTHOR_TABLE")
    suspend fun authors() : List<AppAuthorEntity>

    @Query("select * from $AUTHOR_TABLE where id = :id")
    suspend fun author(id: Int) : AppAuthorEntity
}