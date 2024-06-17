package com.lurenjia534.hobbyexplorer.hobby

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HobbyViewModel(application: Application) : AndroidViewModel(application) {
    private val hobbyDao = HobbyDatabase.getDatabase(application).hobbyDao()

    val allHobbies: LiveData<List<Hobby>> = hobbyDao.getAllHobbies().asLiveData()

    fun insert(hobby: Hobby) = viewModelScope.launch {
        hobbyDao.insert(hobby)
    }
}
