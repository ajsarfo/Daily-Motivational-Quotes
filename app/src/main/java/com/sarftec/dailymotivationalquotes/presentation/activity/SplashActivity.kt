package com.sarftec.dailymotivationalquotes.presentation.activity

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.sarftec.dailymotivationalquotes.databinding.ActivitySplashBinding
import com.sarftec.dailymotivationalquotes.presentation.splash.Splash
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var splash: Splash

    private val binding by lazy {
        ActivitySplashBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val splashItem = splash.fetchSplashItem()
        binding.apply {
            layout.setBackgroundColor(splashItem.background.first)
            splashMessage.apply {
                splashItem.font.setTypefaceAndShadows(this)
                text = splashItem.quote.first
                setTextColor(splashItem.background.second)
            }
            splashInitial.apply {
                text = splashItem.quote.second
                setTextColor(splashItem.background.second)
            }
        }
        setNavigationColor(splashItem.background.first)
        setStatusColor(splashItem.background.first)

        lifecycleScope.launchWhenCreated {
            val timeSpent = measureTimeMillis {
                val imageList: MutableList<Uri> = mutableListOf()
                imageList.addAll(imageStore.authorImageUris())
                imageList.addAll(imageStore.backgroundImageUris())
                imageList.addAll(imageStore.iconImageUris(this@SplashActivity))
                imageLoader.preloadImages(imageList)
            }
            (TimeUnit.SECONDS.toMillis(3) - timeSpent).let { if(it > 0) delay(it) }
            navigateTo(MainActivity::class.java, true)
        }
    }
}