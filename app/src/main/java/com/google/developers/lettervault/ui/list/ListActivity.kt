package com.google.developers.lettervault.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.developers.lettervault.R
import com.google.developers.lettervault.ui.add.AddLetterActivity
import com.google.developers.lettervault.ui.detail.LetterDetailActivity
import com.google.developers.lettervault.ui.setting.SettingActivity
import com.google.developers.lettervault.util.DataViewModelFactory
import com.google.developers.lettervault.util.LETTER_ID
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.content_list.*

class ListActivity : AppCompatActivity() {

    private lateinit var viewModel: LetterViewModel
    private val letterAdapter by lazy {
        LetterAdapter {
            val intent = Intent(this, LetterDetailActivity::class.java).apply {
                putExtra(LETTER_ID, it.id)
            }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val factory = DataViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(LetterViewModel::class.java)

        val pixelSize = resources.getDimensionPixelSize(R.dimen.item_decoration_margin)
        recycler.apply {
            addItemDecoration(ItemDecoration(pixelSize))
            adapter = letterAdapter
        }

        fab?.setOnClickListener {
            val intent = Intent(this, AddLetterActivity::class.java)
            startActivity(intent)
        }

        initLetterObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.future, R.id.opened, R.id.all -> {
                item.isChecked = !item.isChecked
                val itemName = resources.getResourceEntryName(item.itemId)
                try {
                    viewModel.filter(itemName)
                } catch (e: IllegalArgumentException) {
                    Log.e(this.javaClass.name, "Invalid application state: ${e.message}")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initLetterObserver() {
        viewModel.letters.observe(this, Observer { pagedList ->
            letterAdapter.submitList(pagedList)
        })
    }

}
