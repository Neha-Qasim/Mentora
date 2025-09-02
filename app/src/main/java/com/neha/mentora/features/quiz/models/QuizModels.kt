package com.neha.mentora.features.quiz.models


data class QuizQuestion(
    val id: String,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: Int,
    val category: String,
    val ageRangeStart: Int,
    val ageRangeEnd: Int
)




data class QuizAttempt(
    val attemptId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val category: String = "",
    val ageGroup: String = "",
    val score: Int = 0,
    val total: Int = 0,
    val answers: Map<String, Int> = emptyMap()
)

