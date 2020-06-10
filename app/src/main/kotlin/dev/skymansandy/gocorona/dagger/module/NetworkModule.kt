package dev.skymansandy.gocorona.dagger.module

import dagger.Module
import dagger.Provides
import dev.skymansandy.gocorona.constant.AppConstant
import dev.skymansandy.gocorona.constant.DaggerNamedConstants
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    @Named(DaggerNamedConstants.BASE_URL)
    fun providesBaseUrl(): String {
        return AppConstant.BASE_URL
    }

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(
        @Named(DaggerNamedConstants.BASE_URL)
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideCovid19IndiaApi(retrofit: Retrofit): GoCoronaApi {
        return retrofit.create(GoCoronaApi::class.java)
    }
}