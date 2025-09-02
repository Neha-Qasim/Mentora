package com.neha.mentora.features.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    navController: NavController,
    taskId: String? = null,
    vm: TaskViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("Normal") }
    var status by remember { mutableStateOf("Pending") }

    LaunchedEffect(taskId) {
        if (!taskId.isNullOrBlank()) {
            vm.tasks.find { it.id == taskId }?.let { t ->
                title = t.title
                description = t.description
                priority = t.priority
                status = t.status
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == null) "Add Task" else "Edit Task") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    if (title.isNotBlank()) {
                        val t = TaskItem(
                            id = taskId ?: "",
                            title = title.trim(),
                            description = description.trim(),
                            priority = priority,
                            status = status
                        )
                        vm.addOrUpdateTask(t) { ok ->
                            if (ok) navController.popBackStack()
                        }
                    }
                }) { Text("Save") }
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            // Priority dropdown
            var expandedPriority by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedPriority,
                onExpandedChange = { expandedPriority = !expandedPriority }
            ) {
                OutlinedTextField(
                    value = priority,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Priority") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPriority)
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedPriority,
                    onDismissRequest = { expandedPriority = false }
                ) {
                    listOf("High", "Normal", "Low").forEach { p ->
                        DropdownMenuItem(
                            text = { Text(p) },
                            onClick = { priority = p; expandedPriority = false }
                        )
                    }
                }
            }

            // Status dropdown
            var expandedStatus by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedStatus,
                onExpandedChange = { expandedStatus = !expandedStatus }
            ) {
                OutlinedTextField(
                    value = status,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Status") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatus)
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedStatus,
                    onDismissRequest = { expandedStatus = false }
                ) {
                    listOf("Pending", "Done").forEach { s ->
                        DropdownMenuItem(
                            text = { Text(s) },
                            onClick = { status = s; expandedStatus = false }
                        )
                    }
                }
            }
        }
    }
}
