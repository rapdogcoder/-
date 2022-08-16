package com.example.rapstarmusicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rapstarmusicapp.databinding.ActivityMainBinding
import com.example.rapstarmusicapp.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private var _viewBinding: ActivityMainBinding? = null
    private val viewBinding: ActivityMainBinding get() = requireNotNull(_viewBinding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        startActivity(Intent(this, LoginActivity::class.java))

        supportFragmentManager.beginTransaction()
            .replace(viewBinding.fragmentContainer.id, PlayerFragment.newInstance())
            .commit()


    }
}