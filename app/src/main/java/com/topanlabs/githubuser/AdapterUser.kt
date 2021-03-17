package com.topanlabs.githubuser

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class AdapterUser(private val listUser: ArrayList<UserData>) : RecyclerView.Adapter<AdapterUser.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgView: CircleImageView = itemView.findViewById(R.id.img_photo)
        var tvName: TextView = itemView.findViewById(R.id.txt_name)
        var tvDesc: TextView = itemView.findViewById(R.id.txt_description)
        val rootlayout: RelativeLayout = itemView.findViewById(R.id.rlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_row_user,
            parent,
            false
        )
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.imgView.setImageResource(user.avatar)
        holder.tvName.text = user.name
        holder.tvDesc.text = "${user.follower} followers"
        holder.rootlayout.setOnClickListener {
            val intent: Intent = Intent(holder.imgView.context, DetailActivity::class.java)
            intent.putExtra("user", user)
            holder.imgView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

}