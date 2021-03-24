package com.topanlabs.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.topanlabs.githubuser.fragments.SectionsPagerAdapter
import com.topanlabs.githubuser.repository.MainRepository
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text

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
    private lateinit var linLay: LinearLayout
    private lateinit var pBar: ProgressBar
    private var isLoading = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        imgView= findViewById(R.id.img_photo)
        tvName =  findViewById(R.id.txt_name)
        tvUName = findViewById(R.id.txt_username)
        tvCity  = findViewById(R.id.txt_city)
        tvCompany = findViewById(R.id.txt_company)
        tvFlw = findViewById(R.id.txt_followers)
        tvFlwing = findViewById(R.id.txt_following)
        tvRepo = findViewById(R.id.txt_repo)
        linLay = findViewById(R.id.linearLayout)
        pBar = findViewById(R.id.progressBar)
        username = intent.getStringExtra(IN_USERNAME)
        mainRepository = MainRepository(username=username!!)
        loadData()
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadData() {
        GlobalScope.launch(Dispatchers.Main)  {
            var user = mainRepository.getUser()
            user?.apply {
                tvName.text = name
                tvUName.text = login
                tvCity.text = location
                tvCompany.text = company
                tvFlw.text = followers.toString()
                tvFlwing.text = following.toString()
                tvRepo.text = publicRepos.toString()
                Glide.with(this@DetailActivity)
                        .load(avatarUrl)
                        .into(imgView)
            }
            isLoading = false
            pBar.visibility = View.GONE
            linLay.visibility = View.VISIBLE
        }
    }
}