package com.example.siuverse.ui.view.auth.register

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.siuverse.R
import com.example.siuverse.databinding.ActivityRegisterBinding
import com.example.siuverse.ui.model.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupView()

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
       binding.btnRegister.setOnClickListener {
            registerViewModel.register(
                binding.edtTxtUsername.text.toString(),
                binding.edtTxtEmail.text.toString(),
                binding.edtTxtPassword.text.toString()
            ).observe(this) { result ->
                when (result) {
                    is com.example.siuverse.data.repository.Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is com.example.siuverse.data.repository.Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                        val email = binding.edtTxtEmail.text.toString()
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeah!")
                            setMessage("Akun dengan email $email telah siap, silahkan login!")
                            setPositiveButton("Continue") { _, _ ->
                                finish()
                            }
                            create()
                            show()
                        }
                    }

                    is com.example.siuverse.data.repository.Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}