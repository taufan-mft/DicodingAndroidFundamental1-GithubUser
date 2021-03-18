package com.topanlabs.githubuser

import android.content.Context
import android.content.res.Resources

class UserMaker(val context: Context) {

    val names = context.resources.getStringArray(R.array.name)
    val followers = context.resources.getStringArray(R.array.followers)
    val dataPhoto = context.resources.obtainTypedArray(R.array.avatar)
    val followings = context.resources.getStringArray(R.array.following)
    val cities = context.resources.getStringArray(R.array.location)
    val companies = context.resources.getStringArray(R.array.company)
    val usernames = context.resources.getStringArray(R.array.username)
    val listData: ArrayList<UserData>
        get() {
            val list = arrayListOf<UserData>()
            for (pos in names.indices) {
                val merc = UserData().apply {
                    name = names[pos]
                    follower = followers[pos]
                    avatar = dataPhoto.getResourceId(pos, -1)
                    city = cities[pos]
                    company = companies[pos]
                    following = followings[pos]
                    username = usernames[pos]
                }
                list.add(merc)
            }
            return list
        }

}