package com.example.sudokugame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class ActivityPlayGround : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_play_ground)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar,menu);

        return super.onCreateOptionsMenu(menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_logout -> {
                AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes"){ dialog ,_ ->
                        auth.signOut()

                        auth.addAuthStateListener {
                            if (!isFinishing) {
                                if(auth.currentUser == null){
                                    dialog.dismiss()

                                    val intent = Intent(this, ActivityLogin::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    AlertDialog.Builder(this)
                                        .setTitle("Error")
                                        .setMessage("Something went wrong")
                                        .setPositiveButton("Ok"){ dialog ,_ ->
                                            dialog.dismiss()
                                        }
                                        .create()
                                        .show()
                                }
                            }
                        }
                    }
                    .setNegativeButton("No"){ dialog ,_ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}