package com.sarftec.dailymotivationalquotes.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.sarftec.dailymotivationalquotes.application.Setup
import com.sarftec.dailymotivationalquotes.presentation.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoadActivity : BaseActivity() {

    private lateinit var dialog: LoadingDialog

    @Inject
    lateinit var setup: Setup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = LoadingDialog(this, LayoutInflater.from(this))
        dialog.show()
        lifecycleScope.launchWhenCreated {
            setup.createDatabase()
            navigateTo(SplashActivity::class.java, true)
        }
    }
}