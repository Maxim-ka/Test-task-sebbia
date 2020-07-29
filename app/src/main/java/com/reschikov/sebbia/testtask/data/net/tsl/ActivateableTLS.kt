package com.reschikov.sebbia.testtask.data.net.tsl

import okhttp3.OkHttpClient

interface ActivateableTLS {
    fun getClient() : OkHttpClient
}