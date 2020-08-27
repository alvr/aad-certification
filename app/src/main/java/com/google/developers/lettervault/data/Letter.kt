package com.google.developers.lettervault.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model class holds information about the letter and defines table for the Room
 * database with auto generate primary key.
 */
@Entity(tableName = Letter.TABLE_NAME)
data class Letter(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    val id: Long = 0,

    @ColumnInfo(name = SUBJECT_COLUMN)
    val subject: String,

    @ColumnInfo(name = CONTENT_COLUMN)
    val content: String,

    @ColumnInfo(name = CREATED_COLUMN)
    val created: Long,

    @ColumnInfo(name = EXPIRES_COLUMN)
    val expires: Long,

    @ColumnInfo(name = OPENED_COLUMN)
    val opened: Long = 0
) {
    companion object {
        const val TABLE_NAME = "letter"

        const val ID_COLUMN = "id"
        const val SUBJECT_COLUMN = "subject"
        const val CONTENT_COLUMN = "content"
        const val CREATED_COLUMN = "created"
        const val EXPIRES_COLUMN = "expires"
        const val OPENED_COLUMN = "opened"
    }
}
