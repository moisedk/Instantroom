package com.moise.instantroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.moise.instantroom.databinding.ActivitySignupBinding
import com.parse.ParseUser

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnSignup.setOnClickListener{
//
//            val username = binding.etUsername.text.toString()//binding.etUsername.text.toString()
//            val password = binding.etPassword.text.toString()//binding.etPassword.text.toString()
//            singUpUser(username, password)
//        }

    }
    private fun singUpUser(username: String, password: String){
        val user = ParseUser()
// Set fields for the user to be created
        user.username = username
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                goToMainActivity()
                Toast.makeText(this@SignupActivity, "Sign Up successful!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                e.printStackTrace()
                Toast.makeText(this@SignupActivity, "Sign Up failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this@SignupActivity, MainActivity::class.java)
        startActivity(intent)
    }
}