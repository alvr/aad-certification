package com.google.developers.lettervault.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery

/**
 * Room data object for all database interactions.
 *
 * @see Dao
 */
interface LetterDao {

    fun getLetters(query: SupportSQLiteQuery): DataSource.Factory<Int, Letter>

    fun getLetter(letterId: Long): LiveData<Letter>

    fun getRecentLetter()

    fun insert(letter: Letter): Long

    fun insetAll(vararg letter: Letter)

    fun update(letter: Letter)

    fun delete(letter: Letter)

}