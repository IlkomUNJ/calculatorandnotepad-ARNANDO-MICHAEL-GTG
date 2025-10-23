package com.example.myproject.notepad

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

class NotepadViewModel : ViewModel() {
    // State yang mengelola konten teks, termasuk selection
    var editorState by mutableStateOf(TextFieldValue(""))
        private set

    // Clipboard sederhana untuk menyimpan teks yang di-copy/cut
    private var clipboardContent: String? = null

    // --- Core Logic ---

    fun onEditorValueChange(newValue: TextFieldValue) {
        editorState = newValue
    }

    fun cut() {
        val selection = editorState.selection
        if (selection.collapsed) return

        val selectedText = editorState.text.substring(selection.start, selection.end)
        clipboardContent = selectedText

        val newText = editorState.text.removeRange(selection.start, selection.end)

        // Reset selection and update text
        editorState = editorState.copy(
            text = newText,
            selection = TextRange(selection.start)
        )
    }

    fun copy() {
        val selection = editorState.selection
        if (selection.collapsed) return

        val selectedText = editorState.text.substring(selection.start, selection.end)
        clipboardContent = selectedText
    }

    fun paste() {
        val contentToPaste = clipboardContent ?: return
        val selection = editorState.selection
        val currentText = editorState.text

        // Hapus teks yang terseleksi (jika ada) dan sisipkan konten clipboard
        val newText = currentText.replaceRange(selection.start, selection.end, contentToPaste)
        val newCursorPosition = selection.start + contentToPaste.length

        // Update state
        editorState = editorState.copy(
            text = newText,
            selection = TextRange(newCursorPosition)
        )
    }

    fun newFile() {
        editorState = TextFieldValue("", TextRange.Zero)
    }

    // Untuk simulasi "Save" (tanpa implementasi file I/O)
    fun save() {
        // Implementasi save ke file I/O dilewati sesuai petunjuk [cite: 51]
        println("Saving current note: ${editorState.text.take(20)}...")
    }
}