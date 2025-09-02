package com.neha.mentora.features.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(navController: NavController, vm: TaskViewModel = viewModel()) {
    // collect tasks from flow (reliable for recomposition)
    val tasks by vm.tasksFlow.collectAsState()

    var filter by remember { mutableStateOf("All") }
    var toDelete by remember { mutableStateOf<TaskItem?>(null) }

    LaunchedEffect(Unit) { vm.startListening() }
    DisposableEffect(Unit) { onDispose { vm.stopListening() } }

    // compute filtered & priority-sorted display list as derived state for efficiency
    val display by remember(tasks, filter) {
        derivedStateOf {
            // sort by priority descending (High > Normal > Low), then by title
            val priorityValue: (TaskItem) -> Int = {
                when (it.priority.lowercase(Locale.getDefault())) {
                    "high" -> 3
                    "normal" -> 2
                    else -> 1
                }
            }
            tasks
                .sortedWith(compareByDescending<TaskItem> { priorityValue(it) }.thenBy { it.title.lowercase(Locale.getDefault()) })
                .filter { filter.equals("All", ignoreCase = true) || it.status.equals(filter, ignoreCase = true) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("add_edit_task") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Add Task") },
                text = { Text("Add Task") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp)
        ) {
            // Filter buttons row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                listOf("All", "Pending", "Done").forEach { f ->
                    val selected = filter.equals(f, ignoreCase = true)
                    Button(
                        onClick = { filter = f },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(f)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(display, key = { it.id }) { task ->
                    TaskCardRow(
                        task = task,
                        onEdit = { navController.navigate("add_edit_task?taskId=${task.id}") },
                        onDelete = { toDelete = task }
                    )
                }
            }

            // delete confirmation
            toDelete?.let { task ->
                AlertDialog(
                    onDismissRequest = { toDelete = null },
                    title = { Text("Delete task") },
                    text = { Text("Delete \"${task.title}\"?") },
                    confirmButton = {
                        TextButton(onClick = {
                            vm.deleteTask(task.id) { /* optional callback */ }
                            toDelete = null
                        }) { Text("Delete") }
                    },
                    dismissButton = {
                        TextButton(onClick = { toDelete = null }) { Text("Cancel") }
                    }
                )
            }
        }
    }
}

@Composable
fun TaskCardRow(task: TaskItem, onEdit: () -> Unit, onDelete: () -> Unit) {
    val color = when (task.priority.lowercase(Locale.getDefault())) {
        "high" -> Color(0xFFEF9A9A) // light red
        "normal" -> Color(0xFFFFE0B2) // light amber
        else -> Color(0xFFA5D6A7) // light green
    }

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(color)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(task.title, style = MaterialTheme.typography.titleMedium)
                Text(task.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Text("Status: ${task.status}", style = MaterialTheme.typography.bodySmall)
            }

            IconButton(onClick = onEdit) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}
