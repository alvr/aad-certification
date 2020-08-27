package com.google.developers.lettervault.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.google.developers.lettervault.data.DataRepository
import com.google.developers.lettervault.data.Letter
import com.google.developers.lettervault.data.LetterState

/**
 * ViewMode for the HomeActivity only holds recent letter.
 */
class HomeViewModel(dataRepository: DataRepository) : ViewModel() {
    val letter: LiveData<Letter> = dataRepository.getLatestLetter()
}
