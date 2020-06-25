package br.com.johan.johanapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.johan.johanapp.R
import br.com.johan.johanapp.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mLoginViewModel: LoginViewModel
    private lateinit var mToast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // start LoginViewModel
        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        login_button.setOnClickListener { login() }

        login_text_version.setOnClickListener { mLoginViewModel.appVersionClick() }

        login_text_cadastrar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        login_text_forget_password.setOnClickListener {
            startActivity(Intent(this, RecoverPasswordActivity::class.java))
        }

        createObservers()
        confToast()
    }

    // Call function doLogin and update UI
    private fun login() {
        displayButtonLogin(false)
        login_progress_bar.visibility = View.VISIBLE

        val email = login_input_email.editText?.text.toString().trim()
        val password = login_input_password.editText?.text.toString().trim()

        mLoginViewModel.doLogin(email, password)
    }

    // Start Observers
    private fun createObservers() {

        // Set App Version text
        mLoginViewModel.appVersion.observe(this, Observer {
            login_text_version.text = it
        })

        // Observer swapi success
        mLoginViewModel.swapiValidator.observe(this, Observer {
            if (it.success()) {
                startActivity(Intent(this, SwapiActivity::class.java))
            } else {
                mToast.setText(it.failure())
                mToast.show()
            }
        })

        // observe sign status
        mLoginViewModel.loginValidator.observe(this, Observer {
            if (it.success()) {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                mToast.setText(it.failure())
                mToast.show()
            }
            displayButtonLogin(true)
            login_progress_bar.visibility = View.GONE
        })
    }

    // Config. Toast
    private fun confToast() {
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
        mToast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, 0, 150)
    }

    // Show or hide button
    private fun displayButtonLogin(display: Boolean) {
        login_button.isClickable = display
        login_button.isEnabled = display
    }
}
