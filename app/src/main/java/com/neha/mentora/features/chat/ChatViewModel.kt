package com.neha.mentora.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.neha.mentora.features.chat.ChatMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val dbRef: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("chat")

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    init {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ChatMessage>()
                for (child in snapshot.children) {
                    child.getValue(ChatMessage::class.java)?.let { list.add(it) }
                }
                viewModelScope.launch {
                    _messages.value = list.sortedBy { it.timestamp }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun sendMessage(uid: String, name: String, text: String) {
        val msg = ChatMessage(
            senderId = uid,
            senderName = name.ifBlank { "Anonymous" },
            message = text,
            timestamp = System.currentTimeMillis()
        )
        dbRef.push().setValue(msg)
    }

}
