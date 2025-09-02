package com.neha.mentora.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.neha.mentora.R

@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedAvatar by remember { mutableStateOf("male") } // default male

    val isLoading by authViewModel.isLoading.collectAsState()
    val error by authViewModel.error.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF7E57C2)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Register", style = MaterialTheme.typography.headlineMedium, color = Color.White)

                Spacer(modifier = Modifier.height(24.dp))

                // Avatar selection
                Text("Choose Avatar", color = Color.White)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { selectedAvatar = "male" }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_avatar_male),
                                contentDescription = "Male Avatar",
                                tint = if (selectedAvatar == "male") Color.Yellow else Color.White,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                        Text("Male", color = Color.White)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { selectedAvatar = "female" }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_avatar_female),
                                contentDescription = "Female Avatar",
                                tint = if (selectedAvatar == "female") Color.Yellow else Color.White,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                        Text("Female", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Name, Email, Password fields (same as before)
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        if (error != null) authViewModel.clearError()
                    },
                    label = { Text("Full Name", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White,
                        focusedBorderColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (error != null) authViewModel.clearError()
                    },
                    label = { Text("Email", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White,
                        focusedBorderColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        if (error != null) authViewModel.clearError()
                    },
                    label = { Text("Password", color = Color.White) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White,
                        focusedBorderColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Button(
                        onClick = {
                            if (name.isBlank() || email.isBlank() || password.isBlank()) {
                                authViewModel.setError("All fields are required.")
                            } else {
                                val avatarUrl = if (selectedAvatar == "male")
                                    "drawable://ic_avatar_male"
                                else
                                    "drawable://ic_avatar_female"

                                authViewModel.signUpWithEmail(name, email, password, avatarUrl) { success ->
                                    if (success) {
                                        navController.navigate("dashboard") {
                                            popUpTo("register") { inclusive = true }
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("Register", color = Color(0xFF7E57C2))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = { navController.navigate("login") }) {
                    Text("Already have an account? Login", color = Color.White)
                }

                error?.let {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}