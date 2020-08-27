package com.google.developers.lettervault.ui.list

import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.google.developers.lettervault.R
import com.google.developers.lettervault.data.Letter
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
        itemView.setOnClickListener { clickListener(letter) }

        if (letter.expires < System.currentTimeMillis() && letter.opened != 0L) {
            val opened =
                context.getString(R.string.title_opened, simpleDate.format(letter.opened))
        } else {
            if (letter.expires < System.currentTimeMillis()) {
                val ready = context.getString(R.string.letter_ready)
            } else {
                val opening =
                    context.getString(R.string.letter_opening, simpleDate.format(letter.expires))
            }
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
