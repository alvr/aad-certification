package com.google.developers.lettervault.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.developers.lettervault.notification.NotificationWorker
import com.google.developers.lettervault.util.LETTER_ID
import com.google.developers.lettervault.util.LetterLock
import com.google.developers.lettervault.util.PAGE_SIZE
import com.google.developers.lettervault.util.executeThread
import java.util.concurrent.TimeUnit

/**
 * Handles data sources and execute on the correct threads.
 */
class DataRepository(private val letterDao: LetterDao) {

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(context: Context): DataRepository? {
            return instance ?: synchronized(DataRepository::class.java) {
                if (instance == null) {
                    val database = LetterDatabase.getInstance(context)
                    instance = DataRepository(database.letterDao())
                }
                return instance
            }
        }
    }

    /**
     * Get letters with a filtered state for paging.
     */
    fun getLetters(filter: LetterState): LiveData<PagedList<Letter>> {
        return LivePagedListBuilder<Int, Letter>(
            letterDao.getLetters(getFilteredQuery(filter)),
            Config(PAGE_SIZE)
        ).build()
    }

    /**
     * Get the letter with the specified id
     */
    fun getLetter(id: Long) = letterDao.getLetter(id)

    /**
     * Delete the letter
     */
    fun delete(letter: Letter) {
        executeThread {
            letterDao.delete(letter)
        }
    }

    /**
     * Add a letter to database and schedule a notification on
     * when the letter vault can be opened.
     */
    fun save(letter: Letter) = executeThread {
        val id = letterDao.insert(letter)
        val request = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(workDataOf(LETTER_ID to id))
            .setInitialDelay(letter.expires - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance().enqueue(request)
    }

    /**
     * Update database with a decode letter content and update the opened timestamp.
     */
    fun openLetter(letter: Letter) = executeThread {
        val letterCopy = letter.copy(
            subject = LetterLock.retrieveMessage(letter.subject),
            content = LetterLock.retrieveMessage(letter.content),
            opened = System.currentTimeMillis()
        )
        letterDao.update(letterCopy)
    }

    /**
     * Get the last letter added to the database
     */
    fun getLatestLetter() = letterDao.getRecentLetter()

    /**
     * Create a raw query at runtime for filtering the letters.
     */
    private fun getFilteredQuery(filter: LetterState): SimpleSQLiteQuery {
        val now = System.currentTimeMillis()
        val simpleQuery = StringBuilder()
            .append("SELECT * FROM letter ")

        if (filter == LetterState.FUTURE) {
            simpleQuery.append("WHERE expires >= $now OR expires <= $now AND opened IS 0")
        }
        if (filter == LetterState.OPENED) {
            simpleQuery.append("WHERE opened IS NOT 0")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

}
