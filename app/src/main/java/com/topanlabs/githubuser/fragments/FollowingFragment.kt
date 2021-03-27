package com.topanlabs.githubuser.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topanlabs.githubuser.AdapterUser
import com.topanlabs.githubuser.R
import com.topanlabs.githubuser.repository.MainRepository
import com.topanlabs.githubuser.viewmodel.MainViewModel

private const val ARG_PARAM1 = "param1"
class FollowingFragment : Fragment() {
    private lateinit var mainRepo: MainRepository
    private lateinit var model: MainViewModel
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1)!!
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val rView: RecyclerView = view.findViewById(R.id.rv_main)
        val pBar: ProgressBar = view.findViewById(R.id.progressBar)
        val gada: TextView = view.findViewById(R.id.gaketemu)
        val listUserAdapter = AdapterUser()
        rView.adapter = listUserAdapter
        rView.layoutManager = LinearLayoutManager(context)
        mainRepo = MainRepository()
        loadData()
        model.getFollowing().observe(this, { weatherItems ->
            if (weatherItems != null) {
                if (weatherItems.isNotEmpty()) {
                    listUserAdapter.setFollowingData(weatherItems)
                    pBar.visibility = View.GONE
                    rView.visibility = View.VISIBLE
                } else {
                    pBar.visibility = View.GONE
                    rView.visibility= View.GONE
                    gada.visibility = View.VISIBLE
                }
            }
        })
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                FollowingFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
    private fun loadData() {
        model.doFollowing(username)
    }
}