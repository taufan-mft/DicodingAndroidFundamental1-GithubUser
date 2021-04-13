package com.topanlabs.consumerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.topanlabs.consumerapp.helper.UserEntity
import de.hdodenhof.circleimageview.CircleImageView

class AdapterUser : RecyclerView.Adapter<AdapterUser.ListViewHolder>() {
    private val listUser: ArrayList<UserEntity> = ArrayList()

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgView: CircleImageView = itemView.findViewById(R.id.img_photo)
        var tvName: TextView = itemView.findViewById(R.id.txt_name)
    }


    fun setFavoriteData(items: List<UserEntity>) {
        listUser.clear()
        listUser.addAll(items)
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
            .load(user.photo)
            .into(holder.imgView)
        holder.tvName.text = user.username
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

}