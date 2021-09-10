package com.sarftec.dailymotivationalquotes.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.sarftec.dailymotivationalquotes.application.file.moreApps
import com.sarftec.dailymotivationalquotes.application.file.vibrate
import com.sarftec.dailymotivationalquotes.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity() {

    private val binding by lazy {
        ActivityAboutBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.forMoreApps.setOnClickListener {
            dependency.coroutineScope.vibrate(this)
            moreApps()
        }
    }
}