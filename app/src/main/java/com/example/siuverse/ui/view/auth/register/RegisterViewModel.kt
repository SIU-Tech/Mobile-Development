package com.example.siuverse.ui.view.auth.register

import androidx.lifecycle.ViewModel
import com.example.siuverse.data.repository.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun register(name: String, email: String, password: String) =
        repository.register(name, email, password)
}