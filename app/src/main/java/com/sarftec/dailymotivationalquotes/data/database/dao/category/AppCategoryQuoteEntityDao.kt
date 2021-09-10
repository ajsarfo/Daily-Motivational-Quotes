package com.sarftec.dailymotivationalquotes.data.database.dao.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarftec.dailymotivationalquotes.data.database.CATEGORY_QUOTE_TABLE
import com.sarftec.dailymotivationalquotes.data.database.entity.catgeory.AppCategoryQuoteEntity

@Dao
interface AppCategoryQuoteEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quotes: List<AppCategoryQuoteEntity>)

    @Query("update $CATEGORY_QUOTE_TABLE set isFavorite = :isFavorite where id = :id")
    suspend fun update(id: Int, isFavorite: Boolean)

    @Query("select * from $CATEGORY_QUOTE_TABLE")
    suspend fun quotes() : List<AppCategoryQuoteEntity>

    @Query("select * from $CATEGORY_QUOTE_TABLE where categoryId = :categoryId")
    suspend fun quotes(categoryId: Int) : List<AppCategoryQuoteEntity>

    @Query("select * from $CATEGORY_QUOTE_TABLE where isFavorite = 1")
    suspend fun favorites() : List<AppCategoryQuoteEntity>

    @Query("select * from $CATEGORY_QUOTE_TABLE where categoryId = :categoryId and isFavorite = 1")
    suspend fun favorites(categoryId: Int) : List<AppCategoryQuoteEntity>
}