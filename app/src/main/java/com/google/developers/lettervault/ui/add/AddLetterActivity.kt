package com.google.developers.lettervault.ui.add

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.developers.lettervault.R
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.toolbar
import kotlinx.android.synthetic.main.activity_letter_detail.*
import java.text.SimpleDateFormat
import java.util.*

class AddLetterActivity : AppCompatActivity() {

    private lateinit var viewModel: AddLetterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close)
            title = getString(R.string.created_title,
                SimpleDateFormat("MMM d Y, h:mm a", Locale.getDefault()).format(Date())
            )
        }

        val factory = AddLetterViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(AddLetterViewModel::class.java)

        initObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_time -> {
                val calendar = Calendar.getInstance()
                val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
                val currentMinute = calendar.get(Calendar.MINUTE)

                // I use the 24hour clock instead of the 12hour clock because I'm spanish.
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    viewModel.setExpirationTime(hour, minute)
                }, currentHour, currentMinute, true).show()
            }
            R.id.action_save -> {
                viewModel.save(
                    letter_subject?.text?.toString().orEmpty(),
                    letter_content?.text?.toString().orEmpty()
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Added a toast to notify that the letter has been saved. Also, this avoids adding the letter
     * again.
     */
    private fun initObserver() {
        viewModel.saved.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { saved ->
                if (!saved) {
                    letter_content?.error = getString(R.string.cannot_save_message)
                } else {
                    Toast.makeText(this, R.string.letter_saved, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}