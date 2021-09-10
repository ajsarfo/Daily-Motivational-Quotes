package com.sarftec.dailymotivationalquotes.data.database.dao.author

import androidx.room.*
import com.sarftec.dailymotivationalquotes.data.database.AUTHOR_QUOTE_TABLE
import com.sarftec.dailymotivationalquotes.data.database.entity.author.AppAuthorQuoteEntity

@Dao
interface AppAuthorQuoteEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quotes: List<AppAuthorQuoteEntity>)

    @Query("update $AUTHOR_QUOTE_TABLE set isFavorite = :isFavorite where id = :id")
    suspend fun update(id: Int, isFavorite: Boolean)

    @Query("select * from $AUTHOR_QUOTE_TABLE")
    suspend fun quotes() : List<AppAuthorQuoteEntity>

    @Query("select * from $AUTHOR_QUOTE_TABLE where authorId = :authorId")
    suspend fun quotes(authorId: Int) : List<AppAuthorQuoteEntity>

    @Query("select * from $AUTHOR_QUOTE_TABLE where isFavorite = 1")
    suspend fun favorites() : List<AppAuthorQuoteEntity>

    @Query("select * from $AUTHOR_QUOTE_TABLE where authorId = :authorId and isFavorite = 1")
    suspend fun favorites(authorId: Int) : List<AppAuthorQuoteEntity>
}