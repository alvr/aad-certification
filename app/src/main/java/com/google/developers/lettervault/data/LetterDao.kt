package com.google.developers.lettervault.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

/**
 * Room data object for all database interactions.
 *
 * @see Dao
 */
@Dao
interface LetterDao {

    @RawQuery(observedEntities = [Letter::class])
    fun getLetters(query: SupportSQLiteQuery): DataSource.Factory<Int, Letter>

    @Query("SELECT * FROM ${Letter.TABLE_NAME} WHERE ${Letter.ID_COLUMN} = :letterId")
    fun getLetter(letterId: Long): LiveData<Letter>

    @Query("SELECT * FROM ${Letter.TABLE_NAME} ORDER BY ${Letter.CREATED_COLUMN} DESC LIMIT 1")
    fun getRecentLetter(): LiveData<Letter>

    @Insert
    fun insert(letter: Letter): Long

    @Insert
    fun insertAll(vararg letter: Letter)

    @Update
    fun update(letter: Letter)

    @Delete
    fun delete(letter: Letter)

}