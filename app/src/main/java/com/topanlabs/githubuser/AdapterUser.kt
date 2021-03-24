package com.topanlabs.githubuser

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.topanlabs.githubuser.model.Item
import de.hdodenhof.circleimageview.CircleImageView

class AdapterUser() : RecyclerView.Adapter<AdapterUser.ListViewHolder>() {
    private val listUser: ArrayList<Item> = ArrayList()
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgView: CircleImageView = itemView.findViewById(R.id.img_photo)
        var tvName: TextView = itemView.findViewById(R.id.txt_name)
        var tvDesc: TextView = itemView.findViewById(R.id.txt_description)
        val rootlayout: ConstraintLayout = itemView.findViewById(R.id.rlayout)
    }
    fun setData(items: ArrayList<Item>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }
    fun clearData() {
        listUser.clear()
        notifyDataSetChanged()
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
        Glide.with(holder.imgView.context)
            .load(user.avatarUrl)
            .into(holder.imgView)
        holder.tvName.text = user.login
        holder.tvDesc.text = "-"
     /*   holder.rootlayout.setOnClickListener {
            val intent: Intent = Intent(holder.imgView.context, DetailActivity::class.java)
            intent.putExtra("user", user)
            holder.imgView.context.startActivity(intent)
        }*/
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

}