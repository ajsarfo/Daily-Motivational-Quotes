package com.sarftec.dailymotivationalquotes.presentation.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.application.Setup
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartActivity : BaseActivity() {

    @Inject
    lateinit var setup: Setup

    override fun canShowInterstitial(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            if (!setup.isDatabaseCreated()) navigateTo(
                LoadActivity::class.java,
                true,
                R.anim.no_anim,
                R.anim.no_anim
            )
            else navigateTo(
                SplashActivity::class.java,
                true,
                R.anim.no_anim,
                R.anim.no_anim
            )
        }
    }
}