package com.neha.mentora

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import com.neha.mentora.auth.AuthViewModel
import androidx.compose.foundation.Image


@Composable
fun DashboardScreen(
    navController: androidx.navigation.NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val userProfile by authViewModel.userProfile.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF7E57C2))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val avatarPainter = when (userProfile?.avatarUrl) {
                "drawable://ic_avatar_male" -> painterResource(R.drawable.ic_avatar_male)
                "drawable://ic_avatar_female" -> painterResource(R.drawable.ic_avatar_female)
                null, "" -> painterResource(R.drawable.ic_avatar_placeholder)
                else -> rememberAsyncImagePainter(userProfile?.avatarUrl)
            }
            Image(
                avatarPainter,
                contentDescription = null,
                modifier = Modifier.size(56.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(userProfile?.name ?: "Welcome", color = Color.White, fontWeight = FontWeight.Bold)
                Text(userProfile?.email ?: "", color = Color.White, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Dashboard cards
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            DashboardCard("Quizzes", "Take quizzes by category & age") {
                navController.navigate("quiz_category")
            }
            DashboardCard("Tasks", "Manage study tasks") { navController.navigate("tasks") }
            DashboardCard("Chat", "Real-time study group chat") { navController.navigate("chat") }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom actions
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = { navController.popBackStack() }) { Text("Back") }
            Button(
                onClick = {
                    authViewModel.signOut()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2))
            ) {
                Text("Logout", color = Color.White)
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(90.dp).clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(modifier = Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(56.dp).background(Color(0xFF7E57C2), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(title.take(1), color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
