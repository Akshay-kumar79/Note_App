package com.akshaw.noteapp.feature_note.domain.use_cases

import com.akshaw.noteapp.feature_note.data.repository.FakeNoteRepository
import com.akshaw.noteapp.feature_note.domain.model.InvalidNoteException
import com.akshaw.noteapp.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class AddNoteTest {

    private lateinit var addNote: AddNoteUseCase
    private lateinit var getNotes: GetNotesUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        addNote = AddNoteUseCase(fakeNoteRepository)
        getNotes = GetNotesUseCase(fakeNoteRepository)
    }

    @Test(expected = InvalidNoteException::class)
    fun `Add note with blank title, throws InvalidNoteException`() = runBlocking{
        val note = Note(
            title = "",
            content = "content",
            timestamp = 233232,
            color = 233
        )
        addNote(note)
    }

    @Test(expected = InvalidNoteException::class)
    fun `Add note with blank content, throws InvalidNoteException`() = runBlocking{
        val note = Note(
            title = "title",
            content = "",
            timestamp = 233232,
            color = 233
        )
        addNote(note)
    }

    @Test
    fun `Add note with valid title and content, successfully inserts`() = runBlocking{
        val note = Note(
            title = "title",
            content = "content",
            timestamp = 233232,
            color = 233
        )
        assertThat(getNotes().first()).doesNotContain(note)
        addNote(note)
        assertThat(getNotes().first()).contains(note)
    }
}