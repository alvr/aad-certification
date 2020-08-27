package com.google.developers.lettervault.util

import java.util.concurrent.Executors

const val LETTER_ID = "LETTER_ID"
const val NOTIFICATION_CHANNEL_ID = "notify-letter"
const val NOTIFICATION_ID = 32
const val PAGE_SIZE = 20

private val SINGLE_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 * Execute blocks on correct thread, use for data work.
 */
fun executeThread(f: () -> Unit) {
    SINGLE_EXECUTOR.execute(f)
}
