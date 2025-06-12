package com.qrypta.cryptopay

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.qrypta.cryptopay.api.LoginRequest
import com.qrypta.cryptopay.api.LoginResponse
import com.qrypta.cryptopay.api.RetrofitClient

class LoginActivity : AppCompatActivity() {

    private lateinit var userInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var signInButton: Button
    private lateinit var createAccountTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userInput = findViewById(R.id.loginUserInput)
        passwordInput = findViewById(R.id.loginPasswordInput)
        signInButton = findViewById(R.id.loginSignInButton)
        createAccountTextView = findViewById(R.id.createAccountTextView)

        val toolbar = findViewById<Toolbar>(R.id.loginToolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        signInButton.setOnClickListener {
            val user = userInput.text.toString()
            val password = passwordInput.text.toString()

            if (user.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter user and password", Toast.LENGTH_SHORT).show()
            } else {
                val request = LoginRequest(
                    username = user,
                    password = password
                )
                loginUser(request)
            }
        }

        createAccountTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginUser(request: LoginRequest) {
        RetrofitClient.instance.loginUser(request).enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(
                call: retrofit2.Call<LoginResponse>,
                response: retrofit2.Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Login exitoso", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Error en el login", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Fallo: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
