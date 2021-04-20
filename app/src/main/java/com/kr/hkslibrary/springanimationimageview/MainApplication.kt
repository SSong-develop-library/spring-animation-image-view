package com.kr.hkslibrary.springanimationimageview

import android.app.Application
import com.hk.customcardview.util.PixelRatio

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeSingleton()
    }

    private fun initializeSingleton() {
        pixelRatio = PixelRatio(this)
    }

    companion object {
        lateinit var pixelRatio: PixelRatio
    }
}