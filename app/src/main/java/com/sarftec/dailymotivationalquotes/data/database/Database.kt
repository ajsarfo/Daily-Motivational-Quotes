package com.sarftec.dailymotivationalquotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sarftec.dailymotivationalquotes.data.database.dao.author.AppAuthorEntityDao
import com.sarftec.dailymotivationalquotes.data.database.dao.author.AppAuthorQuoteEntityDao
import com.sarftec.dailymotivationalquotes.data.database.dao.category.AppCategoryEntityDao
import com.sarftec.dailymotivationalquotes.data.database.dao.category.AppCategoryQuoteEntityDao
import com.sarftec.dailymotivationalquotes.data.database.entity.author.AppAuthorEntity
import com.sarftec.dailymotivationalquotes.data.database.entity.author.AppAuthorQuoteEntity
import com.sarftec.dailymotivationalquotes.data.database.entity.catgeory.AppCategoryEntity
import com.sarftec.dailymotivationalquotes.data.database.entity.catgeory.AppCategoryQuoteEntity

@Database(
    entities = [
        AppAuthorEntity::class,
        AppAuthorQuoteEntity::class,
        AppCategoryEntity::class,
        AppCategoryQuoteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun authorDao(): AppAuthorEntityDao
    abstract fun authorQuoteDao(): AppAuthorQuoteEntityDao
    abstract fun categoryDao() : AppCategoryEntityDao
    abstract fun categoryQuoteDao() : AppCategoryQuoteEntityDao
}