package com.sarftec.dailymotivationalquotes.data.database.entity.catgeory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.sarftec.dailymotivationalquotes.data.database.CATEGORY_QUOTE_TABLE

@Entity(
    tableName = CATEGORY_QUOTE_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = AppCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class AppCategoryQuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(index = true) val categoryId: Int,
    val message: String,
    val author: String,
    var isFavorite: Boolean = false
)