package com.neha.mentora.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val lastLogin: String = "",
    val avatarUrl: String = "" // ✅ avatar now properly used
)

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().reference

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoggedIn = MutableStateFlow(auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    init {
        if (currentUser != null) {
            fetchUserProfile()
        }

        auth.addAuthStateListener {
            _isLoggedIn.value = it.currentUser != null
            if (it.currentUser != null) {
                fetchUserProfile()
            }
        }
    }

    // ✅ Sign up new user with avatar support
    fun signUpWithEmail(
        name: String,
        email: String,
        password: String,
        avatarUrl: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _error.value = "Invalid email format"
                _isLoading.value = false
                return@launch
            }
            if (password.length < 6) {
                _error.value = "Password must be at least 6 characters"
                _isLoading.value = false
                return@launch
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _isLoading.value = false
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val uid = user?.uid ?: return@addOnCompleteListener

                        val profile = UserProfile(
                            name = name,
                            email = email,
                            lastLogin = Date().toString(),
                            avatarUrl = avatarUrl
                        )

                        db.child("users").child(uid).child("profile")
                            .setValue(profile)
                            .addOnSuccessListener {
                                _userProfile.value = profile
                                onResult(true)
                            }
                            .addOnFailureListener {
                                _error.value = "Failed to save profile"
                                onResult(false)
                            }

                    } else {
                        _error.value = getFirebaseSignUpError(task.exception)
                        onResult(false)
                    }
                }
        }
    }

    // ✅ Sign in existing user with validation
    fun signInWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _error.value = "Invalid email. Example: example@email.com"
                _isLoading.value = false
                return@launch
            }

            if (password.length < 6) {
                _error.value = "Password must be at least 6 characters"
                _isLoading.value = false
                return@launch
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _isLoading.value = false
                    if (task.isSuccessful) {
                        fetchUserProfile()
                        onResult(true)
                    } else {
                        _error.value = getFirebaseSignInError(task.exception)
                        onResult(false)
                    }
                }
        }
    }

    // ---------------- Error helpers ----------------
    private fun getFirebaseSignUpError(exception: Exception?): String {
        return when (exception) {
            is FirebaseAuthWeakPasswordException -> "Password is too weak. Minimum 6 characters required."
            is FirebaseAuthInvalidCredentialsException -> "Invalid email format."
            is FirebaseAuthUserCollisionException -> "Email is already in use."
            else -> exception?.localizedMessage ?: "Sign-up failed. Please try again."
        }
    }

    private fun getFirebaseSignInError(exception: Exception?): String {
        return when (exception) {
            is FirebaseAuthInvalidCredentialsException -> "Invalid credentials. Check email or password."
            is FirebaseAuthInvalidUserException -> "No account found with this email."
            else -> exception?.localizedMessage ?: "Sign-in failed. Please try again."
        }
    }

    // ---------------- Profile helpers ----------------
    private fun fetchUserProfile() {
        val uid = currentUser?.uid ?: return

        db.child("users").child(uid).child("profile")
            .get()
            .addOnSuccessListener { snapshot ->
                snapshot.getValue(UserProfile::class.java)?.let {
                    _userProfile.value = it
                }
            }
            .addOnFailureListener {
                _error.value = "Failed to load profile"
            }
    }

    fun signOut() {
        auth.signOut()
        _isLoggedIn.value = false
        _userProfile.value = null
    }

    // ✅ Fix: logout() for ProfileScreen
    fun logout(navController: NavController) {
        signOut()
        navController.navigate("login") {
            popUpTo("dashboard") { inclusive = true }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun setError(message: String) {
        _error.value = message
    }
}
