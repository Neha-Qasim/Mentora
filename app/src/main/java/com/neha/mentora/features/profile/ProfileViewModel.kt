package com.neha.mentora.features.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private var tasksRef: DatabaseReference? = null
    private var listener: ValueEventListener? = null

    private val _name = MutableStateFlow("Unknown")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow(auth.currentUser?.email ?: "")
    val email: StateFlow<String> = _email

    private val _avatar = MutableStateFlow("")
    val avatar: StateFlow<String> = _avatar

    private val _taskCount = MutableStateFlow(0)
    val taskCount: StateFlow<Int> = _taskCount

    init {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val db = FirebaseDatabase.getInstance().reference.child("users").child(uid)

            // Fetch name & avatar from DB (fallback to FirebaseAuth)
            db.child("profile").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _name.value = snapshot.child("name").getValue(String::class.java)
                        ?: auth.currentUser?.displayName ?: "Unknown"
                    _avatar.value = snapshot.child("avatar").getValue(String::class.java)
                        ?: auth.currentUser?.photoUrl?.toString().orEmpty()
                }
                override fun onCancelled(error: DatabaseError) {}
            })

            // Track task count
            tasksRef = db.child("tasks")
            listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _taskCount.value = snapshot.childrenCount.toInt()
                }
                override fun onCancelled(error: DatabaseError) {}
            }
            tasksRef?.addValueEventListener(listener!!)
        }
    }

    override fun onCleared() {
        super.onCleared()
        listener?.let { l -> tasksRef?.removeEventListener(l) }
    }
}
