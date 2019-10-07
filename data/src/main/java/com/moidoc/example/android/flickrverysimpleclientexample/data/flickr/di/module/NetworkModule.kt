package com.moidoc.example.android.flickrverysimpleclientexample.di.module

import com.google.gson.Gson
import com.moidoc.example.android.flickrverysimpleclientexample.data.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * General network module
 */
@Module
class NetworkModule(private val baseUrl: String) {
    @Provides
    internal fun provideGson(): Gson = Gson()

    @Provides
    internal fun provideClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC

        val builder = OkHttpClient.Builder()

        // ensure httpLoggingInterceptor placed in the end of the interceptors list
        builder.addNetworkInterceptor(httpLoggingInterceptor)

        return builder.build()
    }

    @Provides
    internal fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}