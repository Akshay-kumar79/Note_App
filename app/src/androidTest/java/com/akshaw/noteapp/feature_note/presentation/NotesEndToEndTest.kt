package com.akshaw.noteapp.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akshaw.noteapp.core.util.TestTags
import com.akshaw.noteapp.di.AppModule
import com.akshaw.noteapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.akshaw.noteapp.feature_note.presentation.notes.NotesScreen
import com.akshaw.noteapp.feature_note.presentation.utils.Screen
import com.akshaw.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.activity.setContent {
            NoteAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditNoteScreen.route
                                + "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ){
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }

        }
    }

    @Test
    fun saveNewNote_editAfterwards(){
        composeRule.onNodeWithContentDescription("Add note").performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("test title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput("test content sad mi")
        composeRule.onNodeWithContentDescription("Save note").performClick()

        composeRule.onNodeWithText("test title").assertIsDisplayed()
        composeRule.onNodeWithText("test title").performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).assertTextEquals("test title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).assertTextEquals("test content sad mi")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput(" 2")
        composeRule.onNodeWithContentDescription("Save note").performClick()

        composeRule.onNodeWithText("test title").assertIsDisplayed()
        composeRule.onNodeWithText("test content sad mi 2").assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDescending(){
        for (i in 1..3){
            composeRule.onNodeWithContentDescription("Add note").performClick()

            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("test title $i")
            composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput("test content $i")
            composeRule.onNodeWithContentDescription("Save note").performClick()
        }

        composeRule.onNodeWithText("test title 1").assertIsDisplayed()
        composeRule.onNodeWithText("test title 2").assertIsDisplayed()
        composeRule.onNodeWithText("test title 3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithContentDescription("Title").performClick()
        composeRule.onNodeWithContentDescription("Descending").performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("test title 3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("test title 2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("test title 1")
    }
}