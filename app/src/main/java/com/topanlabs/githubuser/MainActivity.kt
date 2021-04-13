package com.topanlabs.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topanlabs.githubuser.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var rvMobil: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var listUserAdapter: AdapterUser
    private lateinit  var pBar: ProgressBar
    private lateinit var petunjuk: TextView
    private lateinit var gaketemu: TextView
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
        petunjuk = findViewById(R.id.petunjuk)
        gaketemu = findViewById(R.id.gaketemu)
        mainViewModel.getWeathers().observe(this, { weatherItems ->

            if (weatherItems != null) {
                if (weatherItems.isNotEmpty()) {
                    listUserAdapter.setData(weatherItems)
                    isLoading = false
                    pBar.visibility = View.GONE
                    rvMobil.visibility = View.VISIBLE
                    gaketemu.visibility=View.GONE
                } else {
                    rvMobil.visibility = View.GONE
                    gaketemu.visibility = View.VISIBLE
                    pBar.visibility= View.GONE
                }
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
            override fun onQueryTextSubmit(query: String): Boolean {
                petunjuk.visibility=View.GONE
                changeLoading()
                val text = query
                mainViewModel.doSearch(text)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(p0: View?) {

            }

            override fun onViewDetachedFromWindow(p0: View?) {
                petunjuk.visibility = View.VISIBLE
                rvMobil.visibility = View.GONE
                gaketemu.visibility = View.GONE
                pBar.visibility = View.GONE
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeLoading() {
        if (isLoading) {
            isLoading = false
            pBar.visibility = View.GONE
            rvMobil.visibility = View.VISIBLE
            gaketemu.visibility = View.GONE
        } else {
            isLoading = true
            rvMobil.visibility = View.GONE
            pBar.visibility = View.VISIBLE

        }
    }

}