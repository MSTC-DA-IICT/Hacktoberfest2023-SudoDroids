package com.example.sudokugame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sudokugame.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class ActivityLogin : AppCompatActivity() {

    companion object{
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.show()
        supportActionBar?.title = "Login"
        MainActivity.auth = FirebaseAuth.getInstance()

        val user = MainActivity.auth.currentUser

        if (user != null){
            startActivity(Intent(this,ActivityPlayGround::class.java))
            finish()
        }

        binding.registerNow.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener{
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                MainActivity.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,ActivityPlayGround::class.java))
                        finish()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}