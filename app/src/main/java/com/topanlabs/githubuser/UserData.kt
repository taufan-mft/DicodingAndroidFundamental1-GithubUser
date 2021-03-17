package com.topanlabs.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    var username: String ="",
    var name: String="",
    var avatar: Int = 0,
    var follower: String ="",
    var city: String= "",
    var company: String = "",
    var following: String=""

) :Parcelable
