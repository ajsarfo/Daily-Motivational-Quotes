package com.sarftec.dailymotivationalquotes.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.appodeal.ads.Appodeal
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.application.file.vibrate
import com.sarftec.dailymotivationalquotes.databinding.ActivityFavoriteQuoteBinding
import com.sarftec.dailymotivationalquotes.presentation.binding.FavoriteQuoteBinding

class FavoriteQuoteActivity : BaseActivity() {

    private val binding by lazy {
        ActivityFavoriteQuoteBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //Show banner
        Appodeal.setBannerViewId(R.id.activity_favorite_quote_banner)

        setUpToolbar()
        intent.getBundleExtra(ACTIVITY_BUNDLE)?.let { bundle ->
            val title = bundle.getString(FAVORITE_QUOTE_TITLE)
            val subtitle = bundle.getString(FAVORITE_QUOTE_SUBTITLE)
            val imageString = bundle.getString(FAVORITE_QUOTE_IMAGE)
            if(title != null && subtitle != null && imageString != null) {
                binding.binding = FavoriteQuoteBinding(
                    dependency,
                    title,
                    subtitle,
                    imageString
                ).also { it.init() }
                binding.executePendingBindings()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_BOTTOM)
    }

    fun setUpToolbar() {
        binding.back.setOnClickListener {
            dependency.apply {
                coroutineScope.vibrate(context)
            }
            onBackPressed()
        }
    }
}