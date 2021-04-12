package com.topanlabs.githubuser

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topanlabs.githubuser.viewmodel.UserViewModel
import com.topanlabs.githubuser.viewmodel.UserViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var rvFavorite: RecyclerView
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as UserApplication).userRepository)
    }
    private lateinit var listUserAdapter: AdapterUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        rvFavorite = findViewById(R.id.rv_favorite)
        listUserAdapter = AdapterUser()
        rvFavorite.adapter = listUserAdapter
        rvFavorite.layoutManager = LinearLayoutManager(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.favorites)
        userViewModel.allLikedUsers.observe(this) { users ->
            users.let { listUserAdapter.setFavoriteData(it) }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}