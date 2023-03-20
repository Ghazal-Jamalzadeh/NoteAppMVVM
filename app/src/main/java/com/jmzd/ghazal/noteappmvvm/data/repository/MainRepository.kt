package com.jmzd.ghazal.noteappmvvm.data.repository

import com.jmzd.ghazal.noteappmvvm.data.database.NoteDao
import com.jmzd.ghazal.noteappmvvm.data.model.NoteEntity
import javax.inject.Inject


class MainRepository @Inject constructor(private val dao: NoteDao){
    fun allNotes() = dao.getAllNotes()
    fun searchNotes(search: String) = dao.searchNote(search)
    fun filterNotes(filter: String) = dao.filetNote(filter)
    suspend fun deleteNote(entity: NoteEntity) = dao.deleteNote(entity)
}