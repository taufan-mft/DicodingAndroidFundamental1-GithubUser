package com.topanlabs.githubuser

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.topanlabs.githubuser.db.UserEntity
import com.topanlabs.githubuser.fragments.SectionsPagerAdapter
import com.topanlabs.githubuser.repository.MainRepository
import com.topanlabs.githubuser.viewmodel.UserViewModel
import com.topanlabs.githubuser.viewmodel.UserViewModelFactory
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object {
        val IN_USERNAME = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private var username: String? = ""
    private lateinit var mainRepository: MainRepository
    private lateinit var imgView: CircleImageView
    private lateinit var tvName: TextView
    private lateinit var tvUName: TextView
    private lateinit var tvCity: TextView
    private lateinit var tvCompany: TextView
    private lateinit var tvFlw: TextView
    private lateinit var tvFlwing: TextView
    private lateinit var tvRepo: TextView
    private lateinit var linLay: ConstraintLayout
    private lateinit var pBar: ProgressBar
    private lateinit var fButton: FloatingActionButton
    private var isLiked = false
    private lateinit var photourl: String
    private lateinit var userEntity: UserEntity
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as UserApplication).userRepository)
    }
    private var isLoading = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        imgView = findViewById(R.id.img_photo)
        tvName = findViewById(R.id.txt_name)
        tvUName = findViewById(R.id.txt_username)
        tvCity = findViewById(R.id.txt_city)
        tvCompany = findViewById(R.id.txt_company)
        tvFlw = findViewById(R.id.txt_followers)
        tvFlwing = findViewById(R.id.txt_following)
        tvRepo = findViewById(R.id.txt_repo)
        linLay = findViewById(R.id.linlay)
        pBar = findViewById(R.id.progressBar)
        fButton = findViewById(R.id.floatingActionButton)
        username = intent.getStringExtra(IN_USERNAME)
        mainRepository = MainRepository()
        loadData()
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username!!)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        fButton.setOnClickListener {
            if (isLiked) {
                fButton.setColorFilter(Color.BLACK)
                userViewModel.delete(userEntity)
            } else {
                val user = UserEntity(username = username!!, photo = photourl)
                userViewModel.insert(user)
                isLiked = true
                fButton.setColorFilter(Color.WHITE)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadData() {
        GlobalScope.launch(Dispatchers.Main) {
            var user = mainRepository.getUser(username = username!!)
            user?.apply {
                tvName.text = name
                tvUName.text = login
                tvCity.text = location
                tvCompany.text = company
                tvFlw.text = followers.toString()
                tvFlwing.text = following.toString()
                tvRepo.text = publicRepos.toString()
                photourl = avatarUrl
                Glide.with(this@DetailActivity)
                    .load(avatarUrl)
                    .into(imgView)
            }
            isLoading = false
            pBar.visibility = View.GONE
            linLay.visibility = View.VISIBLE

            isLiked = userViewModel.isLiked(username!!)
            if (isLiked) {
                fButton.setColorFilter(Color.WHITE)
                userEntity = userViewModel.getUser(username!!)
            }
        }
    }
}