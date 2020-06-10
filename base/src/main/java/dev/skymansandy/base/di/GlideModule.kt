package dev.skymansandy.base.di

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides

@Module
class GlideModule {

    @Provides
    @AppScope
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions()
    }

    @Provides
    @AppScope
    fun provideRequestManager(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }
}