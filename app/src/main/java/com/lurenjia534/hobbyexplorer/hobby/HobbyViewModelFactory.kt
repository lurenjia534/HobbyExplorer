package com.lurenjia534.hobbyexplorer.hobby

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HobbyViewModelFactory(
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HobbyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HobbyViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}