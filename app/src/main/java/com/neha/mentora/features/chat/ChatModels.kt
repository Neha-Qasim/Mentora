package com.neha.mentora.features.chat

data class ChatMessage(
    val senderId: String = "",
    val senderName: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
