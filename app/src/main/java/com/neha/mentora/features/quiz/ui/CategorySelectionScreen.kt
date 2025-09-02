package com.neha.mentora.features.quiz.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.neha.mentora.features.quiz.data.QuestionsProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectionScreen(navController: NavController) {
    var selectedCategory by remember { mutableStateOf(QuestionsProvider.categories().first()) }
    var selectedAge by remember { mutableStateOf("10") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Choose Quiz") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Select Category:")
            DropdownMenuBox(
                options = QuestionsProvider.categories(),
                selected = selectedCategory,
                onSelect = { selectedCategory = it }
            )

            Spacer(Modifier.height(12.dp))

            Text("Enter Age:")
            OutlinedTextField(
                value = selectedAge,
                onValueChange = { v -> selectedAge = v },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val ageInt = selectedAge.toIntOrNull() ?: 10
                    navController.navigate("quiz/$selectedCategory/$ageInt")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Quiz")
            }
        }
    }
}

@Composable
fun DropdownMenuBox(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    OutlinedButton(onClick = { expanded = true }) {
        Text(selected)
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSelect(it)
                        expanded = false
                    }
                )
            }
        }
    }
}
