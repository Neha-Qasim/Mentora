package com.neha.mentora.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, vm: ProfileViewModel = viewModel()) {
    val name = vm.name.collectAsState().value
    val email = vm.email.collectAsState().value
    val avatar = vm.avatar.collectAsState().value
    val taskCount = vm.taskCount.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Avatar
            if (avatar.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 8.dp)
                )
            } else {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 8.dp)
                )
            }

            // Name + Email
            Text(text = name.ifEmpty { "Unknown User" }, style = MaterialTheme.typography.titleLarge)
            Text(text = email, style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(20.dp))

            // Task Count
            Text(text = "Tasks: $taskCount", style = MaterialTheme.typography.bodyLarge)

            Spacer(Modifier.height(24.dp))

            // ✅ Quiz History Button
            Button(
                onClick = { navController.navigate("quiz_history") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Quiz History")
            }
        }
    }
}
