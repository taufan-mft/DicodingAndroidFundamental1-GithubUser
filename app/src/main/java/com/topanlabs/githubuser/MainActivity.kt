package com.topanlabs.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    var list: ArrayList<UserData> = arrayListOf()
    private lateinit var rvMobil: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMobil = findViewById(R.id.rv_heroes)
        rvMobil.setHasFixedSize(true)
        val userMaker = UserMaker(context = applicationContext)
        list.addAll(userMaker.listData)
        showRecyclerList()
    }

    private fun showRecyclerList() {

            rvMobil.layoutManager = LinearLayoutManager(this)
            val listUserAdapter = AdapterUser(list)
            rvMobil.adapter = listUserAdapter
    }

}