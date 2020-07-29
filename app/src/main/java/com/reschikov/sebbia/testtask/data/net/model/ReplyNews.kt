package com.reschikov.sebbia.testtask.data.net.model

import com.google.gson.annotations.SerializedName

data class ReplyNews(val code : Int, @SerializedName("news") val fullNews : FullNews)