package com.topanlabs.consumerapp

import android.content.UriMatcher
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.topanlabs.consumerapp.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recView: RecyclerView
    private lateinit var listUserAdapter: AdapterUser

    companion object {
        const val AUTHORITY = "com.topanlabs.githubuser"
        const val SCHEME = "content"
        const val TABLE_NAME = "user_table"
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recView = findViewById(R.id.rv_favorite)
        supportActionBar?.title = "Consumer Favorite"
        recView.layoutManager = LinearLayoutManager(this)
        listUserAdapter = AdapterUser()
        recView.adapter = listUserAdapter
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, 1)
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        loadNotesAsync()
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)

            }
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                listUserAdapter.setFavoriteData(notes)
            } else {
                Snackbar.make(recView, "Tidak ada data saat ini", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}