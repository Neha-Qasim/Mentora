package com.neha.mentora.features.tasks

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskViewModel : ViewModel() {
    private val _backingList = mutableStateListOf<TaskItem>()           // snapshot list for internal use
    val tasks: List<TaskItem> get() = _backingList

    // new: expose as StateFlow so composables can reliably collect it
    private val _tasksFlow = MutableStateFlow<List<TaskItem>>(emptyList())
    val tasksFlow: StateFlow<List<TaskItem>> = _tasksFlow

    private var listener: ValueEventListener? = null
    private val dbRef get() = FirebaseAuth.getInstance().currentUser?.uid?.let {
        FirebaseDatabase.getInstance().reference.child("users").child(it).child("tasks")
    }

    fun startListening(onChange: (List<TaskItem>) -> Unit = {}) {
        val ref = dbRef ?: return
        if (listener != null) return // already listening

        listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _backingList.clear()
                val list = mutableListOf<TaskItem>()
                snapshot.children.forEach { c ->
                    c.getValue(TaskItem::class.java)?.let {
                        _backingList.add(it)
                        list.add(it)
                    }
                }
                // publish snapshot to flow (this triggers recomposition for collectors)
                _tasksFlow.value = list.sortedBy { it.title } // base order; UI does its own priority sorting
                onChange(list)
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        ref.addValueEventListener(listener as ValueEventListener)
    }

    fun stopListening() {
        dbRef?.let { listener?.let { l -> it.removeEventListener(l) } }
        listener = null
    }

    fun addOrUpdateTask(task: TaskItem, onComplete: (Boolean) -> Unit = {}) {
        val ref = dbRef ?: run { onComplete(false); return }
        val key = if (task.id.isBlank()) ref.push().key!! else task.id
        val payload = task.copy(id = key)
        ref.child(key).setValue(payload)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun deleteTask(id: String, onComplete: (Boolean) -> Unit = {}) {
        val ref = dbRef ?: run { onComplete(false); return }
        ref.child(id).removeValue()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}
