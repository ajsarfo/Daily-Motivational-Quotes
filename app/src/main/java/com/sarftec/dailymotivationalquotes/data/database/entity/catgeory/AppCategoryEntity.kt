package com.sarftec.dailymotivationalquotes.data.database.entity.catgeory

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sarftec.dailymotivationalquotes.data.database.CATEGORY_TABLE


@Entity(tableName = CATEGORY_TABLE)
class AppCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val quoteSize: Int
)