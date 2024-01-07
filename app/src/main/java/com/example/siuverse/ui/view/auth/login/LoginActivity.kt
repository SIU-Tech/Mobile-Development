package com.example.siuverse.ui.view.auth.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.siuverse.data.repository.Result
import com.example.siuverse.databinding.ActivityLoginBinding
import com.example.siuverse.ui.model.UserModel
import com.example.siuverse.ui.model.ViewModelFactory
import com.example.siuverse.ui.view.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            loginViewModel.login(
                binding.edtTxtEmail.text.toString(),
                binding.edtTxtPassword.text.toString()
            ).observe(this){ result ->
                when(result){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success-> {
                        binding.progressBar.visibility = View.GONE
                        val email = binding.edtTxtEmail.text.toString()
                        val token = result.data.user.token
                        val userModel = UserModel(email, token)
                        loginViewModel.saveSession(userModel)
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeah!")
                            setMessage("Login Successfully")
                            setPositiveButton("Continue") { _, _ ->
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                    is Result.Error-> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}