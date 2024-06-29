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
    private val sharePreferences = application.getSharedPreferences("HobbyPrefs", 0)

    val allHobbies: LiveData<List<Hobby>> = hobbyDao.getAllHobbies().asLiveData()

    private val _displayedHobbies = MutableLiveData<List<Hobby>>()
    val displayedHobbies: LiveData<List<Hobby>> = _displayedHobbies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchResults = MutableLiveData<List<Hobby>>()  // 新增
    val searchResults: LiveData<List<Hobby>> = _searchResults  // 新增

    var scrollPosition: Int
        get() = sharePreferences.getInt("scroll_position", 0)
        set(value) = sharePreferences.edit().putInt("scroll_position", value).apply()

    var isDataLoaded = false

    fun updateDisplayedHobbies() {
        viewModelScope.launch {
            _isLoading.value = true
            val hobbies = hobbyDao.getAllHobbies().first()
            _displayedHobbies.postValue(hobbies.shuffled().take(10))
            _isLoading.value = false
        }
    }
    fun getHobbyById(hobbyId:String): LiveData<Hobby?> {
        return hobbyDao.getHobbyById(hobbyId)
    }

    fun searchHobbies(query: String) {
       viewModelScope.launch {
           viewModelScope.launch {
               val allHobbiesList = hobbyDao.getAllHobbies().first()
               _searchResults.postValue(allHobbiesList.filter { it.info?.contains(query, ignoreCase = true)
                   ?: false })
           }
        }
    }

    fun saveCurrentHobbyId(hobbyId: String) {
      return sharePreferences.edit().putString("current_hobby_id", hobbyId).apply()
    }

    fun getCurrentHobbyId(): String? {
        return sharePreferences.getString("current_hobby_id", null)
    }

}