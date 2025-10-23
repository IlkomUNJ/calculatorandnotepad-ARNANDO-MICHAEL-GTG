package com.example.myproject.notepad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // Import Ikon Standar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Import Ikon Back yang baru

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotepadScreen(
    onBack: () -> Unit,
    viewModel: NotepadViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            NotepadTopAppBar(
                onBack = onBack,
                onNew = viewModel::newFile,
                onSave = viewModel::save,
                onCut = viewModel::cut,
                onCopy = viewModel::copy,
                onPaste = viewModel::paste
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        // Activity 1: Prepare a notepad canvas realized with textfield then styling
        OutlinedTextField(
            value = viewModel.editorState,
            onValueChange = viewModel::onEditorValueChange,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFFFFBE0)) // Light Yellow/Cream for notepad
                .padding(16.dp),
            placeholder = { Text("Start typing your note...") },
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            )
        )
    }
}

// Activity 2: Prepare the top appbar menu and handle menu action
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotepadTopAppBar(
    onBack: () -> Unit,
    onNew: () -> Unit,
    onSave: () -> Unit,
    onCut: () -> Unit,
    onCopy: () -> Unit,
    onPaste: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("Simple Notepad Editor") },
        navigationIcon = {
            IconButton(onClick = onBack) {
                // Menggunakan AutoMirrored.Filled.ArrowBack
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            // New (Icons.Filled.Create)
            IconButton(onClick = onNew) {
                Icon(Icons.Filled.Create, contentDescription = "New Note")
            }
            // Save (Icons.Filled.Save)
            IconButton(onClick = onSave) {
                Icon(Icons.Filled.Save, contentDescription = "Save Note")
            }

            // More Menu for Cut/Copy/Paste
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More Actions")
            }

            // Dropdown Menu for Cut, Copy, Paste actions
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Cut (Icons.Filled.ContentCut)
                DropdownMenuItem(
                    text = { Text("Cut") },
                    onClick = {
                        onCut()
                        expanded = false
                    },
                    leadingIcon = { Icon(Icons.Filled.ContentCut, contentDescription = null) }
                )
                // Copy (Icons.Filled.ContentCopy)
                DropdownMenuItem(
                    text = { Text("Copy selection to clipboard") },
                    onClick = {
                        onCopy()
                        expanded = false
                    },
                    leadingIcon = { Icon(Icons.Filled.ContentCopy, contentDescription = null) }
                )
                // Paste (Icons.Filled.ContentPaste)
                DropdownMenuItem(
                    text = { Text("Paste") },
                    onClick = {
                        onPaste()
                        expanded = false
                    },
                    leadingIcon = { Icon(Icons.Filled.ContentPaste, contentDescription = null) }
                )
            }
        }
    )
}