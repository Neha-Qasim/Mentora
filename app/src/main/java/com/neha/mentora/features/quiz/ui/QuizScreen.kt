package com.neha.mentora.features.quiz.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.neha.mentora.features.quiz.models.QuizAttempt
import com.neha.mentora.features.quiz.viewmodel.QuizViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(
    navController: NavController,
    category: String,
    age: Int,
    quizVm: QuizViewModel = viewModel()
) {
    LaunchedEffect(Unit) { quizVm.startSession(category, age) }

    val questions by quizVm.sessionQuestions.collectAsState()
    if (questions.isEmpty()) {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("No questions available for $category at age $age")
            Spacer(Modifier.height(12.dp))
            Button(onClick = { navController.popBackStack() }) { Text("Back") }
        }
        return
    }

    var index by remember { mutableStateOf(0) }
    var remaining by remember { mutableStateOf(10) }
    val answers = remember { mutableStateMapOf<String, Int>() }
    var selected by remember { mutableStateOf(-1) }
    var finished by remember { mutableStateOf(false) }
    var showFeedback by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(index) {
        remaining = 10
        selected = -1
        showFeedback = false
        while (remaining > 0 && !showFeedback) {
            delay(1000)
            remaining--
        }
        if (remaining == 0 && selected == -1) {
            answers[questions[index].id] = -1
            if (index < questions.lastIndex) index++ else finished = true
        }
    }

    val q = questions[index]

    if (finished) {
        val finalScore = questions.count { answers[it.id] == it.correctAnswer }
        val attempt = QuizAttempt(
            category = q.category,
            ageGroup = "${q.ageRangeStart}-${q.ageRangeEnd}",
            score = finalScore,
            total = questions.size,
            answers = answers.toMap()
        )
        quizVm.saveAttempt(attempt)

        // ✅ Save attempt to Firebase
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val db = FirebaseDatabase.getInstance()
                .getReference("users").child(uid).child("quiz_history")
            val key = db.push().key ?: System.currentTimeMillis().toString()
            db.child(key).setValue(attempt)
        }

        navController.navigate("quiz_result/$finalScore/${questions.size}") {
            popUpTo("quiz_category") { inclusive = false }
        }
        return
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Question ${index + 1} of ${questions.size}", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text(q.questionText, style = MaterialTheme.typography.titleMedium)
            }
        }

        Spacer(Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            q.options.forEachIndexed { i, opt ->
                val isCorrect = i == q.correctAnswer
                val isSelected = i == selected
                val bgColor by animateColorAsState(
                    when {
                        showFeedback && isSelected && isCorrect -> MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        showFeedback && isSelected && !isCorrect -> MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
                        showFeedback && isCorrect -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        else -> MaterialTheme.colorScheme.surface
                    }, label = "optionColor"
                )

                Card(
                    modifier = Modifier.fillMaxWidth().selectable(
                        selected = isSelected,
                        onClick = {
                            if (selected == -1) {
                                selected = i
                                answers[q.id] = i
                                showFeedback = true
                                scope.launch {
                                    delay(1500)
                                    if (index < questions.lastIndex) index++ else finished = true
                                }
                            }
                        }
                    ),
                    colors = CardDefaults.cardColors(containerColor = bgColor)
                ) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(opt)
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Time left: $remaining s")
            Button(onClick = {
                if (!showFeedback) {
                    answers[q.id] = selected
                    if (index < questions.lastIndex) index++ else finished = true
                }
            }) { Text("Skip") }
        }
    }
}
