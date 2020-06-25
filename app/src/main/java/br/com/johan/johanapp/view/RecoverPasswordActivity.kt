package br.com.johan.johanapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.johan.johanapp.R
import br.com.johan.johanapp.viewmodel.RecoverPasswordViewModel
import kotlinx.android.synthetic.main.activity_recover_password.*

class RecoverPasswordActivity : AppCompatActivity() {

    private lateinit var mRecoverViewModel: RecoverPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        mRecoverViewModel = ViewModelProvider(this).get(RecoverPasswordViewModel::class.java)

        recover_button.setOnClickListener {
            recoverPass()
        }

        observers()
    }

    private fun recoverPass(){
        val email = recover_input_email.editText?.text.toString().trim()

        mRecoverViewModel.doRecoverPassword(email)

        displayButtonLogin(false)
        recover_progress_bar.visibility = View.VISIBLE
    }

    private fun observers(){
        mRecoverViewModel.recoverPasswordValidator.observe(this, Observer {
            if (it.success()) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else{
                Toast.makeText(this, it.failure(), Toast.LENGTH_SHORT).show()

                displayButtonLogin(true)
                recover_progress_bar.visibility = View.GONE
            }
        })
    }

    private fun displayButtonLogin(display: Boolean) {
        recover_button.isClickable = display
        recover_button.isEnabled = display
    }

}
