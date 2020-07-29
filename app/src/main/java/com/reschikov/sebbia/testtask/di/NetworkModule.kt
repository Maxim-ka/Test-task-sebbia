package com.reschikov.sebbia.testtask.di

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.reschikov.sebbia.testtask.data.net.NetWorkProvider
import com.reschikov.sebbia.testtask.data.net.RequestTesttaskSebbia
import com.reschikov.sebbia.testtask.data.net.tsl.ActivateableTLS
import com.reschikov.sebbia.testtask.data.net.tsl.ClientOkHttp
import com.reschikov.sebbia.testtask.data.repository.Requested
import dagger.Binds
import dagger.Lazy
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL_SERVER = "http://testtask.sebbia.com/"

@Module
abstract class NetworkModule{

    companion object{

        @SuppressLint("JvmStaticProvidesInObjectDetector")
        @JvmStatic
        @Provides
        fun provideRequestTestTask(activateableTLS : Lazy<ActivateableTLS>) : RequestTesttaskSebbia {
            return getRetrofit(activateableTLS)
                .create(RequestTesttaskSebbia::class.java)
        }

        private fun getRetrofit(activateableTLS : Lazy<ActivateableTLS>) : Retrofit {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Retrofit
                    .Builder()
                    .client(activateableTLS.get().getClient())
                    .baseUrl(URL_SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            } else {
                Retrofit
                    .Builder()
                    .baseUrl(URL_SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }

        @SuppressLint("JvmStaticProvidesInObjectDetector")
        @JvmStatic
        @Provides
        fun provideConnectivityManager(context: Context) : ConnectivityManager {
            return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
    }

    @Binds
    abstract fun bindRequested(netWorkProvider: NetWorkProvider) : Requested

    @Binds
    abstract fun bindActivateableTLS(clientOkHttp : ClientOkHttp) : ActivateableTLS
}