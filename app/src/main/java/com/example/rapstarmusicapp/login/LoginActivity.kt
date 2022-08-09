package com.example.rapstarmusicapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rapstarmusicapp.MainActivity
import com.example.rapstarmusicapp.R
import com.example.rapstarmusicapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {ActivityLoginBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            login()
        }
        binding.registerText.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    private fun login() {
        Firebase.auth.signInWithEmailAndPassword(binding.idTxt.text.toString(),binding.passwordTxt.text.toString()).addOnCompleteListener{
            task ->
            if (task.isSuccessful){
                Toast.makeText(this, "전송 성공", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
            }
            else{
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "전송 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}