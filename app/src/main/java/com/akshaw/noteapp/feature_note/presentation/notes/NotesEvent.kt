package com.akshaw.noteapp.feature_note.presentation.notes

import com.akshaw.noteapp.feature_note.domain.model.Note
import com.akshaw.noteapp.feature_note.domain.utils.NoteOrderBy

sealed class NotesEvent{
    data class Order(val noteOrderBy: NoteOrderBy): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
