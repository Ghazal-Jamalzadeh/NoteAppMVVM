package com.jmzd.ghazal.noteappmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.noteappmvvm.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvvm.data.repository.NoteRepository
import com.jmzd.ghazal.noteappmvvm.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    //spinners
    val categoriesList = MutableLiveData<MutableList<String>>()
    val prioritiesList = MutableLiveData<MutableList<String>>()

    /*
    * اگر lunch() را به این صورت بنویسیم هم درست است و به صورت پیش فرض روی ترد main میرود
    * ولی میخوایم کار با تردها و جا به جا شدن بین ترد ها را یاد بگیریم پس خودمون بهش Dispatchers هم میدیم
    * */
    fun loadCategoriesData() = viewModelScope.launch(Dispatchers.IO) {
        val data = mutableListOf(WORK, EDUCATION, HOME, HEALTH)
        categoriesList.postValue(data)
    }

    fun loadPrioritiesData() = viewModelScope.launch(Dispatchers.IO) {
        val data = mutableListOf(HIGH, NORMAL, LOW)
        prioritiesList.postValue(data)
    }

    fun saveEditNote(isEdit: Boolean, entity: NoteEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            if (isEdit) {
                repository.updateNote(entity)
            } else {
                repository.saveNote(entity)

            }
        }
}