package com.neha.mentora.features.quiz.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.neha.mentora.features.quiz.models.QuizAttempt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuizHistoryViewModel : ViewModel() {
    private val _attempts = MutableStateFlow<List<QuizAttempt>>(emptyList())
    val attempts: StateFlow<List<QuizAttempt>> = _attempts

    private var listener: ValueEventListener? = null
    private var ref: DatabaseReference? = null

    init {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            ref = FirebaseDatabase.getInstance()
                .reference.child("users").child(uid).child("quiz_history")
            listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<QuizAttempt>()
                    for (child in snapshot.children) {
                        val attempt = child.getValue(QuizAttempt::class.java)?.copy(attemptId = child.key ?: "")
                        attempt?.let { list.add(it) }
                    }
                    _attempts.value = list.sortedByDescending { it.timestamp }
                }

                override fun onCancelled(error: DatabaseError) {}
            }
            ref?.addValueEventListener(listener as ValueEventListener)
        }
    }

    fun deleteAttempt(attemptId: String) {
        ref?.child(attemptId)?.removeValue()
    }

    override fun onCleared() {
        super.onCleared()
        listener?.let { l -> ref?.removeEventListener(l) }
    }
}
