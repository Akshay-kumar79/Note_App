package com.akshaw.noteapp.feature_note.domain.use_cases

import com.akshaw.noteapp.feature_note.domain.model.InvalidNoteException
import com.akshaw.noteapp.feature_note.domain.model.Note
import com.akshaw.noteapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("The title of the note is blank")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("The content of the note is blank")
        }
        repository.insertNote(note)
    }

}