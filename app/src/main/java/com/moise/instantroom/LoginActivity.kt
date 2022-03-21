package com.moise.instantroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.moise.instantroom.databinding.ActivityLoginBinding
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ParseUser.getCurrentUser() != null) // This ParseUser object is probably a unique singleton for this application
            goToMainActivity()

        binding.btLogin.setOnClickListener{
            val username = binding.etUsername.text.toString()//binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()//binding.etPassword.text.toString()
            loginUser(username, password)
        }
        binding.btnSignUp.setOnClickListener{
            val username = binding.etUsername.text.toString()//binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()//binding.etPassword.text.toString()
            singUpUser(username, password)

        }

    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({
            user, e ->
            if(user != null) {
                goToMainActivity()
                finish()
            }

            else {
                Log.e(TAG, "loginUser: username: $username password:$password ${e.message}")
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }

        }))
    }
    private fun singUpUser(username: String, password: String){
        val user = ParseUser()
// Set fields for the user to be created
        user.username = username
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                goToMainActivity()
                Toast.makeText(this@LoginActivity, "Sign Up successful!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                e.printStackTrace()
                Toast.makeText(this@LoginActivity, "Sign Up failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object{
        private const val TAG = "LoginActivity"
    }
}