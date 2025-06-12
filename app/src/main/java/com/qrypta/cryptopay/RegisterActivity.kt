package com.qrypta.cryptopay

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.qrypta.cryptopay.api.RegisterRequest
import com.qrypta.cryptopay.api.RegisterResponse
import com.qrypta.cryptopay.api.RetrofitClient
import com.qrypta.cryptopay.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.welcomeUserPassButton.setOnClickListener {
            val request = RegisterRequest(
                full_name = binding.registerFullNameInput.text.toString(),
                username = binding.registerUsernameInput.text.toString(),
                email = binding.registerEmailInput.text.toString(),
                phone = binding.registerPhoneNumberInput.text.toString(),
                password = binding.registerPasswordInput.text.toString()
            )
            registerUser(request)
        }
    }

    private fun registerUser(request: RegisterRequest) {
        RetrofitClient.instance.registerUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, response.body()?.message ?: "Registro exitoso", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this@RegisterActivity, "Error en el registro", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Fallo: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
