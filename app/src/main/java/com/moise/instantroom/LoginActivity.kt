package com.moise.instantroom

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
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

        binding.btnLogin.setOnClickListener{
            val username = binding.etUsername.text.toString()//binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()//binding.etPassword.text.toString()
            loginUser(username, password)
        }
        binding.btnSignupWithFb.setOnClickListener{
            Toast.makeText(this, "Signing up with Facebook!", Toast.LENGTH_SHORT).show()
        }
        onClickSignup()
        onClickLoginHelp()

    }
    private fun onClickLoginHelp() {
        val text = "Forgot your login details? Get help logging in"
        val spannableString = SpannableString(text)
        val clickSpan = object : ClickableSpan(){
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                Toast.makeText(this@LoginActivity, "Help is on its way!!", Toast.LENGTH_SHORT).show()
            }
        }
        spannableString.setSpan(clickSpan, text.indexOf("G"), text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvGetLobingHelp.text = spannableString
        binding.tvGetLobingHelp.movementMethod = LinkMovementMethod.getInstance()
        binding.tvGetLobingHelp.highlightColor = Color.TRANSPARENT

    }

    private fun onClickSignup() {
        val spannableString = SpannableString("Don't have an account? Sign up.")
        val clickableSpan = object: ClickableSpan() {
            override fun onClick(widget: View) {
                finish()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.isFakeBoldText = true
            }
        }
        spannableString.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvSignup.text = spannableString
        binding.tvSignup.movementMethod = LinkMovementMethod.getInstance()
        binding.tvSignup.highlightColor = Color.TRANSPARENT
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
    companion object{
        private const val TAG = "LoginActivity"
    }
}