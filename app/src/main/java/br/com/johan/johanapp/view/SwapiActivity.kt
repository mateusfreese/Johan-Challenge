package br.com.johan.johanapp.view

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.johan.johanapp.R
import br.com.johan.johanapp.view.adapter.SwapiAdapter
import br.com.johan.johanapp.viewmodel.SwapiViewModel
import kotlinx.android.synthetic.main.activity_swapi.*


class SwapiActivity : AppCompatActivity() {

    private lateinit var mSwapiViewModel: SwapiViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swapi)
        mSwapiViewModel = ViewModelProvider(this).get(SwapiViewModel::class.java)

        observers()

        settingsRecycler()
    }


    private fun observers(){
        mSwapiViewModel.mutableResults.observe(this, Observer {
            val adapter = SwapiAdapter(it)
            swapi_recycler_view.adapter = adapter
        })
    }

    private fun settingsRecycler() {
        val layoutManager = LinearLayoutManager(applicationContext)
        swapi_recycler_view.layoutManager = layoutManager
        swapi_recycler_view.setHasFixedSize(true)
        swapi_recycler_view.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
    }

}
