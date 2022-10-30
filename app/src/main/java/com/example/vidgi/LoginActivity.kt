package com.example.vidgi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vidgi.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private  val context: Context get() = this

    private  val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button2.setOnClickListener{ login()}

        var isRemember = Prefs.getBoolean("remember")
        if (isRemember) {
            binding.editTextPassword.setText(Prefs.getString("password"))
            binding.editTextTextPersonName.setText(Prefs.getString("user"))
            binding.remember.isChecked = isRemember
        }
    }

    fun login() {
        val intent = Intent(context, HomeActivity::class.java)
        val isRemember = binding.remember.isChecked

        Prefs.setBoolean("remember", isRemember)
        if (isRemember) {
            Prefs.setString("user", binding.editTextTextPersonName.text.toString())
            Prefs.setString("password", binding.editTextPassword.text.toString())
        } else {
            Prefs.setString("user", "")
            Prefs.setString("password", "")
        }


        startActivity(intent)
    }
}