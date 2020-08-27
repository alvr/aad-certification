package com.google.developers.lettervault.ui.list

import android.view.View
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.developers.lettervault.R
import com.google.developers.lettervault.data.Letter
import kotlinx.android.synthetic.main.letter_list_item.view.*
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * View holds a letter for RecyclerView.
 */
class LetterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var letter: Letter
    private val context = itemView.context
    private val simpleDate = SimpleDateFormat("MMM d Y, h:mm a", Locale.getDefault())

    fun bindData(letter: Letter, clickListener: (Letter) -> Unit) {
        this.letter = letter

        // First: Text for the state
        // Second: Show lock
        val footer = if (letter.expires < System.currentTimeMillis() && letter.opened != 0L) {
            context.getString(R.string.title_opened, simpleDate.format(letter.opened)) to false
        } else {
            if (letter.expires < System.currentTimeMillis()) {
                context.getString(R.string.letter_ready) to true
            } else {
                context.getString(R.string.letter_opening, simpleDate.format(letter.expires)) to true
            }
        }

        itemView.apply {
            setOnClickListener { clickListener(letter) }

            letter_subject?.text = letter.subject
            letter_content?.apply {
                text = letter.content
                isGone = footer.second
            }
            letter_state?.text = footer.first
            letter_lock?.isVisible = footer.second
            letter_mini_lock?.isVisible = letter.opened != 0L
        }
    }

    /**
     * This method is used during automated tests.
     *
     * DON'T REMOVE THIS METHOD
     */
    @VisibleForTesting
    fun getLetter(): Letter = letter

}
