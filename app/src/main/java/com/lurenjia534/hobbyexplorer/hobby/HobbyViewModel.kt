package com.lurenjia534.hobbyexplorer.hobby

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class HobbyViewModel(application: Application) : AndroidViewModel(application) {
    private val hobbyDao = HobbyDatabase.getDatabase(application).hobbyDao()

    val allHobbies: LiveData<List<Hobby>> = hobbyDao.getAllHobbies().asLiveData()

    private val _displayedHobbies = MutableLiveData<List<Hobby>>()
    val displayedHobbies: LiveData<List<Hobby>> = _displayedHobbies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun updateDisplayedHobbies() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(500)  // 延迟0.5秒钟
            val hobbies = hobbyDao.getAllHobbies().first()
            _displayedHobbies.postValue(hobbies.shuffled().take(3))
            _isLoading.value = false
        }
    }
    fun getHobbyById(hobbyId:String): LiveData<Hobby?> {
        return hobbyDao.getHobbyById(hobbyId)
    }
}