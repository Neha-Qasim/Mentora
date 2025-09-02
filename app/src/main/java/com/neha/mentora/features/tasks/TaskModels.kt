package com.neha.mentora.features.tasks

data class TaskItem(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val priority: String = "Normal",
    val status: String = "Pending"
)
