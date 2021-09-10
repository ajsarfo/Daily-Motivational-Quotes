package com.sarftec.dailymotivationalquotes.data.database.entity.author

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.sarftec.dailymotivationalquotes.data.database.AUTHOR_QUOTE_TABLE

@Entity(
    tableName = AUTHOR_QUOTE_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = AppAuthorEntity::class,
            parentColumns = ["id"],
            childColumns = ["authorId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class AppAuthorQuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(index = true) val authorId: Int,
    val message: String,
    var isFavorite: Boolean = false
)