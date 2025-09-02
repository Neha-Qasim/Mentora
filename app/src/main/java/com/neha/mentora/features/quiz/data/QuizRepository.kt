package com.neha.mentora.features.quiz.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.neha.mentora.features.quiz.models.QuizAttempt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object QuizRepository {
    private val _attempts = MutableStateFlow<List<QuizAttempt>>(emptyList())
    val attempts: StateFlow<List<QuizAttempt>> = _attempts

    fun setAttempts(list: List<QuizAttempt>) {
        _attempts.value = list
    }

    fun addAttempt(a: QuizAttempt) {
        _attempts.value = _attempts.value + a
    }

    fun deleteAttempt(attemptId: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseDatabase.getInstance()
            .reference.child("users").child(uid).child("quizAttempts").child(attemptId)
            .removeValue()
    }

    fun clear() {
        _attempts.value = emptyList()
    }
}
