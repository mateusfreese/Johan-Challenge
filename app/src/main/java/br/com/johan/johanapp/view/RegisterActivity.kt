package br.com.johan.johanapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.johan.johanapp.R
import br.com.johan.johanapp.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mRegisterViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Start Register View Model
        mRegisterViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        register_button.setOnClickListener {
            displayButtonLogin(false)
            register()
        }

        register_text_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        observers()
    }

    // Start Observers
    private fun observers(){
        mRegisterViewModel.registerValidator.observe(this, Observer {
            if (it.success()){
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, it.failure(), Toast.LENGTH_SHORT).show()
                displayButtonLogin(true)
                register_progress_bar.visibility = View.GONE
            }
        })
    }

    // Take text from fields, parse to string and call function doRegister()
    private fun register(){
        register_progress_bar.visibility = View.VISIBLE
        displayButtonLogin(false)

        val name = register_input_name.editText?.text.toString().trim()
        val email = register_input_email.editText?.text.toString().trim()
        val password = register_input_password.editText?.text.toString().trim()
        val confirmPassword = register_input_conf_password.editText?.text.toString().trim()

        mRegisterViewModel.doRegister(name, email, password, confirmPassword)
    }

    // Show or hide register button
    private fun displayButtonLogin(display: Boolean) {
        register_button.isClickable = display
        register_button.isEnabled = display
    }

}
