package com.neha.mentora.features.quiz.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.neha.mentora.features.quiz.data.QuizRepository
import com.neha.mentora.features.quiz.models.QuizAttempt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuizViewModel : ViewModel() {
    private val _sessionQuestions = MutableStateFlow<List<com.neha.mentora.features.quiz.models.QuizQuestion>>(emptyList())
    val sessionQuestions: StateFlow<List<com.neha.mentora.features.quiz.models.QuizQuestion>> = _sessionQuestions

    // attempts kept in repository (optional)
    val attempts: StateFlow<List<QuizAttempt>> = QuizRepository.attempts

    fun startSession(category: String, age: Int, count: Int = 5) {
        // your QuestionsProvider usage (kept as before from your project)
        val picked = com.neha.mentora.features.quiz.data.QuestionsProvider.pickFor(category, age, count)
        _sessionQuestions.value = picked
    }

    fun saveAttempt(attempt: QuizAttempt) {
        // keep in repo (in-memory) for app session
        QuizRepository.addAttempt(attempt)

        // persist in Firebase under users/{uid}/quiz_history with push key
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val ref = FirebaseDatabase.getInstance().getReference("users").child(uid).child("quiz_history")
        val key = ref.push().key ?: return
        val toSave = attempt.copy(attemptId = key)
        ref.child(key).setValue(toSave)
    }
}
