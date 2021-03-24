package com.topanlabs.githubuser

import android.app.SearchManager
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topanlabs.githubuser.viewmodel.MainViewModel
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var rvMobil: RecyclerView
    private lateinit var searchView: EditText
    private lateinit var mainViewModel: MainViewModel
    private lateinit var listUserAdapter: AdapterUser
    private lateinit  var pBar: ProgressBar
    private var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMobil = findViewById(R.id.rv_heroes)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        rvMobil.layoutManager = LinearLayoutManager(this)
        listUserAdapter = AdapterUser()
        rvMobil.adapter = listUserAdapter
        pBar = findViewById(R.id.progressBar)
        mainViewModel.getWeathers().observe(this, { weatherItems ->

            if (weatherItems != null) {
                Log.d("FARIN", (weatherItems.toString()))
                listUserAdapter.setData(weatherItems)
                isLoading = false
                pBar.visibility = View.GONE
                rvMobil.visibility = View.VISIBLE
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /*
            Gunakan method ini ketika search selesai atau OK
             */
            override fun onQueryTextSubmit(query: String): Boolean {
                changeLoading()
                val text = query
                mainViewModel.doSearch(text)
                return true
            }

            /*
            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
             */
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun changeLoading() {
        if (isLoading) {
            isLoading = false
            pBar.visibility = View.GONE
            rvMobil.visibility = View.VISIBLE
        } else {
            isLoading = true
            rvMobil.visibility = View.GONE
            pBar.visibility = View.VISIBLE

        }
    }

}