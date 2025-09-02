package com.neha.mentora.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.neha.mentora.SplashScreen
import com.neha.mentora.auth.LoginScreen
import com.neha.mentora.auth.RegisterScreen
import com.neha.mentora.auth.onboarding.OnboardingScreen
import com.neha.mentora.DashboardScreen
import com.neha.mentora.auth.AuthViewModel
import com.neha.mentora.features.profile.ProfileScreen
import com.neha.mentora.features.quiz.ui.CategorySelectionScreen
import com.neha.mentora.features.quiz.ui.QuizScreen
import com.neha.mentora.features.quiz.ui.ResultScreen
import com.neha.mentora.features.quiz.ui.QuizHistoryScreen
import com.neha.mentora.features.tasks.TasksScreen
import com.neha.mentora.features.tasks.AddEditTaskScreen
import com.neha.mentora.features.tasks.TaskViewModel
import com.neha.mentora.features.chat.ChatScreen
import com.neha.mentora.features.profile.ProfileViewModel
@Composable
fun AppNavGraph(navController: NavHostController, startDestination: String = "splash") {
    val authViewModel: AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("splash") { SplashScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel) }

        composable("dashboard") { DashboardScreen(navController, authViewModel) }

        // ✅ FIXED: use ProfileViewModel inside ProfileScreen
        composable("profile") {
            val profileVm: ProfileViewModel = viewModel()
            ProfileScreen(navController, profileVm)
        }

        // Quiz History (reachable only from Profile)
        composable("quiz_history") { QuizHistoryScreen(navController) }

        // Category selection
        composable("quiz_category") { CategorySelectionScreen(navController) }

        // Quiz with category + age
        composable(
            route = "quiz/{category}/{age}",
            arguments = listOf(
                navArgument("category") { type = NavType.StringType },
                navArgument("age") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "Math"
            val age = backStackEntry.arguments?.getInt("age") ?: 10
            QuizScreen(navController, category, age)
        }

        composable(
            route = "quiz_result/{score}/{total}",
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 0
            ResultScreen(navController, score, total)
        }

        // Tasks
        composable("tasks") {
            val taskVm: TaskViewModel = viewModel()
            TasksScreen(navController, taskVm)
        }

        // ✅ FIXED: Correct parameter order
        composable(
            route = "add_edit_task?taskId={taskId}",
            arguments = listOf(navArgument("taskId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val taskVm: TaskViewModel = viewModel()
            AddEditTaskScreen(navController, taskId, taskVm)
        }

        // Chat
        composable("chat") {
            ChatScreen(navController)
        }
    }
}
