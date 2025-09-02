package com.neha.mentora.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    vm: ChatViewModel = viewModel()
) {
    val messages by vm.messages.collectAsState()
    var input by remember { mutableStateOf("") }

    val currentUser = FirebaseAuth.getInstance().currentUser
    val uid = currentUser?.uid ?: ""
    val displayName = currentUser?.displayName?.takeIf { !it.isNullOrBlank() }
        ?: currentUser?.email?.substringBefore("@")
        ?: "Unknown"

    fun dateLabel(ts: Long): String {
        val cal = Calendar.getInstance().apply { timeInMillis = ts }
        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance().apply { add(Calendar.DATE, -1) }
        return when {
            cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) -> "Today"
            cal.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) &&
                    cal.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR) -> "Yesterday"
            else -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(ts))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    placeholder = { Text("Type a message") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (input.isNotBlank()) {
                            // ✅ pass uid, name, and text as required by ChatViewModel
                            vm.sendMessage(uid, displayName, input.trim())
                            input = ""
                        }
                    }
                ) {
                    Text("Send")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Group messages by day and show a date header (WhatsApp style)
            val grouped = messages.groupBy { dateLabel(it.timestamp) }
            grouped.forEach { (label, dayMessages) ->
                item {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            label,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
                items(dayMessages) { msg ->
                    val isMe = msg.senderId == uid
                    val bubbleColor =
                        if (isMe) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        else MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                    ) {
                        Column(
                            horizontalAlignment = if (isMe) Alignment.End else Alignment.Start,
                            modifier = Modifier.widthIn(max = 280.dp)
                        ) {
                            // Sender name (the registered name)
                            Text(
                                msg.senderName,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Box(
                                modifier = Modifier
                                    .background(bubbleColor, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Column {
                                    Text(msg.message, style = MaterialTheme.typography.bodyMedium)
                                    Text(
                                        SimpleDateFormat("hh:mm a", Locale.getDefault())
                                            .format(Date(msg.timestamp)),
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.align(Alignment.End)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
