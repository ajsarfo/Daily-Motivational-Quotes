package com.sarftec.dailymotivationalquotes.data.database.entity.author

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sarftec.dailymotivationalquotes.data.database.AUTHOR_TABLE

@Entity(tableName = AUTHOR_TABLE)
class AppAuthorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val quoteSize: Int
)