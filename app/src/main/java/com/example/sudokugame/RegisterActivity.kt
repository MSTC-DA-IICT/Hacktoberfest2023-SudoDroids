package com.example.sudokugame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sudokugame.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    companion object{
        lateinit var auth: FirebaseAuth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Register"
        MainActivity.auth = FirebaseAuth.getInstance()

        val user = MainActivity.auth.currentUser

        if (user != null){
            startActivity(Intent(this,ActivityPlayGround::class.java))
            finish()
        }

        binding.loginNow.setOnClickListener{
            startActivity(Intent(this,ActivityLogin::class.java))
            finish()
        }

        binding.registerBtn.setOnClickListener{
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                MainActivity.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"Registration Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,ActivityPlayGround::class.java))
                        finish()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this,it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}