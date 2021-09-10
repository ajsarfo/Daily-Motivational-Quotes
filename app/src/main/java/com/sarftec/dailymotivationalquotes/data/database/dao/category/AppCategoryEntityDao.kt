package com.sarftec.dailymotivationalquotes.data.database.dao.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarftec.dailymotivationalquotes.application.model.category.AppCategory
import com.sarftec.dailymotivationalquotes.data.database.CATEGORY_TABLE
import com.sarftec.dailymotivationalquotes.data.database.entity.catgeory.AppCategoryEntity

@Dao
interface AppCategoryEntityDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(author: AppCategoryEntity) : Long

    @Query("select * from $CATEGORY_TABLE")
    suspend fun categories() : List<AppCategoryEntity>

    @Query("select * from $CATEGORY_TABLE where id =:id")
    suspend fun category(id: Int) : AppCategory
}