package com.test.androiddevelopersexample.application

import android.app.Application
import android.util.Log
import android.widget.Toast
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import coil.util.CoilUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.test.androiddevelopersexample.R
import okhttp3.OkHttpClient

/**
 * Created by ignaciodeandreisdenis on 7/20/20.
 */
class MyApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Firebase", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.e("Firebase", token)
        })
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
