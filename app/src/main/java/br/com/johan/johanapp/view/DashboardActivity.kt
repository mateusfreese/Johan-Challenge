package br.com.johan.johanapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.johan.johanapp.R
import br.com.johan.johanapp.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var mDashboardActivity: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        mDashboardActivity = ViewModelProvider(this).get(DashboardViewModel::class.java)

        dashboard_button_anotations.setOnClickListener {
            startActivity(Intent(this,NoteActivity::class.java))
        }

        dashboard_button_logout.setOnClickListener {
            logout()
        }

        observers()

    }

    private fun observers(){
        mDashboardActivity.userLoggedName.observe(this, Observer {
            dashboard_welcome_user.text = "Bem Vindo, $it!"
        })
    }

    private fun logout(){
        mDashboardActivity.doLogout()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
