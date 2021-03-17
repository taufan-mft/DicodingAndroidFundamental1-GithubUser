package com.topanlabs.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val imgView: CircleImageView = findViewById(R.id.img_photo)
        val tvName: TextView = findViewById(R.id.txt_name)
        val tvUName: TextView = findViewById(R.id.txt_username)
        val tvCity: TextView = findViewById(R.id.txt_city)
        val tvCompany: TextView = findViewById(R.id.txt_company)
        val tvFlw: TextView = findViewById(R.id.txt_followers)
        val tvFlwing: TextView = findViewById(R.id.txt_following)
        val tvRepo: TextView = findViewById(R.id.txt_repo)
        intent.getParcelableExtra<UserData>("user")?.apply{
            imgView.setImageResource(avatar)
            tvName.text = name
            tvUName.text = username
            tvCity.text = city
            tvCompany.text = company
            tvFlw.text = follower
            tvFlwing.text = following
        }
    }
}