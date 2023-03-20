package com.jmzd.ghazal.noteappmvvm.data.repository

import com.jmzd.ghazal.noteappmvvm.data.database.NoteDao
import com.jmzd.ghazal.noteappmvvm.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject  constructor(private val dao : NoteDao){
   suspend fun saveNote(entity: NoteEntity) = dao.saveNote(entity)
   suspend fun updateNote(entity: NoteEntity) = dao.updateNote(entity)
   fun getNote(id : Int ) : Flow<NoteEntity> = dao.getNote(id = id )
}