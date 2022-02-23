package com.test.androiddevelopersexample.application

import android.app.Application
import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import coil.util.CoilUtils
import okhttp3.OkHttpClient

/**
 * Created by ignaciodeandreisdenis on 7/20/20.
 */
class MyApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
    }

    // Coil image provider
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .componentRegistry {
                add(SvgDecoder(applicationContext))
            }
//            .componentRegistry {
//                if (SDK_INT >= 28) {
//                    add(ImageDecoderDecoder(applicationContext))
//                } else {
//                    add(GifDecoder())
//                }
//            }
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .build()
    }
}
