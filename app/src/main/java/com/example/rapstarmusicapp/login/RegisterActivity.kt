package com.example.rapstarmusicapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rapstarmusicapp.R
import com.example.rapstarmusicapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private var _viewBinding : ActivityRegisterBinding? = null
    private val viewBinding : ActivityRegisterBinding = requireNotNull(_viewBinding)

    override fun onCreate(savedInstanceState: Bundle?) {
        _viewBinding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

    auth = Firebase.auth
    viewBinding.registerBtn.setOnClickListener {
        val id = viewBinding.emailText.text.toString()
        val password = viewBinding.passwordText.text.toString()
        val name = viewBinding.nameText.text.toString()
        register(id, password, name)
    }
    }
    fun register(email:String, password:String, name:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                    task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "전송 성공", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "전송 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }
}