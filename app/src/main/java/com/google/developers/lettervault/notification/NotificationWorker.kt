package com.google.developers.lettervault.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.preference.PreferenceManager
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.developers.lettervault.R
import com.google.developers.lettervault.ui.detail.LetterDetailActivity
import com.google.developers.lettervault.util.LETTER_ID
import com.google.developers.lettervault.util.NOTIFICATION_CHANNEL_ID
import com.google.developers.lettervault.util.NOTIFICATION_ID

/**
 * Run a work to show a notification on a background thread by the {@link WorkManger}.
 */
class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val letterId: Long = inputData.getLong(LETTER_ID, 0)

    /**
     * Create an intent with extended data to the letter.
     */
    private fun getContentIntent(): PendingIntent? {
        val intent = Intent(applicationContext, LetterDetailActivity::class.java).apply {
            putExtra(LETTER_ID, letterId)
        }
        return TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            return@run getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    override fun doWork(): Result {
        val prefManager = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val shouldNotify = prefManager.getBoolean(
            applicationContext.getString(R.string.pref_key_notify),
            false
        )

        return Result.success()
    }

}
