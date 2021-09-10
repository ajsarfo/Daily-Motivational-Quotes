package com.sarftec.dailymotivationalquotes.presentation.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.application.Dependency
import com.sarftec.dailymotivationalquotes.application.imageloader.BitmapImageLoader
import com.sarftec.dailymotivationalquotes.application.imagestore.ImageStore
import com.sarftec.dailymotivationalquotes.application.manager.NetworkManager
import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    
    @Inject
    lateinit var repository: ApplicationRepository

    @Inject
    lateinit var imageStore: ImageStore

    @Inject
    lateinit var imageLoader: BitmapImageLoader
    
    @Inject
    lateinit var networkManager: NetworkManager

    protected val dependency by lazy {
        Dependency(this, lifecycleScope, imageStore, imageLoader)
    }

    //This is called by fragments also
    fun <T> navigateTo(
        klass: Class<T>,
        finish: Boolean = false,
        slideIn: Int = R.anim.slide_in_right,
        slideOut: Int = R.anim.slide_out_left,
        bundle: Bundle? = null
    ) {
        val intent = Intent(this, klass).also {
            it.putExtra(ACTIVITY_BUNDLE, bundle)
        }
        startActivity(intent)
        if (finish) finish()
        overridePendingTransition(slideIn, slideOut)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    protected fun setStatusBarBackgroundLight() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun setNavigationBarBackgroundLight() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    protected fun setStatusColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = color
                if (color == Color.WHITE) setStatusBarBackgroundLight()
            }
        }
    }


    protected fun setNavigationColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                navigationBarColor = color
                if (color == Color.WHITE) setNavigationBarBackgroundLight()
            }
        }
    }

    companion object {
        const val ACTIVITY_BUNDLE = "activity_bundle"
        const val FAVORITE_QUOTE_TITLE = "favorite_title"
        const val FAVORITE_QUOTE_SUBTITLE = "favorite_subtitle"
        const val FAVORITE_QUOTE_IMAGE = "favorite_image"
    }
}