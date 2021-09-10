package com.sarftec.dailymotivationalquotes.presentation.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.appodeal.ads.Appodeal
import com.appodeal.ads.Native
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.application.file.*
import com.sarftec.dailymotivationalquotes.application.imagestore.toAssetUri
import com.sarftec.dailymotivationalquotes.application.manager.InterstitialManager
import com.sarftec.dailymotivationalquotes.databinding.ActivityMainBinding
import com.sarftec.dailymotivationalquotes.databinding.LayoutRatingsDialogBinding
import com.sarftec.dailymotivationalquotes.presentation.binding.MainBinding
import com.sarftec.dailymotivationalquotes.presentation.fragment.main.AuthorFragment
import com.sarftec.dailymotivationalquotes.presentation.fragment.main.CategoryFragment
import com.sarftec.dailymotivationalquotes.presentation.listener.ActivityListener
import com.sarftec.dailymotivationalquotes.presentation.manager.RatingsManager
import com.sarftec.dailymotivationalquotes.presentation.tools.RatingsDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity(), ActivityListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    private val ratingsManager by lazy {
        RatingsManager(
            lifecycleScope,
            RatingsDialog(
                LayoutRatingsDialogBinding.inflate(
                    LayoutInflater.from(this),
                    binding.root,
                    false
                )
            )
        )
    }

    private val interstitialManager by lazy {
        InterstitialManager(
            this,
            networkManager,
            listOf(1, 3, 4, 3)
        )
    }

    private var currentFragment: Fragment? = null

    private var fragmentId = AUTHOR_FRAGMENT

    private var fragmentSelected = false

    private var exitApp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //Initialize Appodeal
        Appodeal.setNativeAdType(Native.NativeAdType.NoVideo)
        Appodeal.setAutoCache(Appodeal.NATIVE, false)
        Appodeal.setBannerViewId(R.id.main_banner)
        Appodeal.initialize(
            this,
            getString(R.string.appodeal_app_id),
            Appodeal.BANNER_VIEW or Appodeal.INTERSTITIAL or Appodeal.NATIVE
        )
        //Perform other setups
        setupToolbar()
        setUpDrawer()
        setUpNavigationView()
        configureNavigationMenu()
        configureNavigationDrawer()
        setFragment(AuthorFragment().also { currentFragment = it })
        lifecycleScope.launchWhenStarted {
            ratingsManager.init()
        }
    }

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_VIEW)
    }

    override fun onBackPressed() {
        with(binding.drawerLayout) {
            if (isDrawerOpen(GravityCompat.START)) closeDrawer(GravityCompat.START)
            else callExit()
        }
    }

    private fun callExit() {
        if (exitApp) finish()
        else {
            exitApp = true
            toast("Press BACK once again to exit!")
            lifecycleScope.launch {
                delay(2000L)
                exitApp = false
            }
        }
    }

    private fun setupToolbar() {
        with(binding.mainToolbar) {
            menu.setOnClickListener { binding.drawerLayout.openDrawer(GravityCompat.START) }
            layoutBinding = MainBinding(
                dependency,
                {
                    dependency.apply { coroutineScope.vibrate(context) }
                    navigateTo(FavoriteListActivity::class.java)
                },
                {
                    dependency.apply { coroutineScope.vibrate(context) }
                    rateApp()
                }
            ).also { it.init() }
            executePendingBindings()
        }
    }

    private fun setUpDrawer() {
        val imageView = binding.navigationView.getHeaderView(0).findViewById<ImageView>(
            R.id.drawer_header_image
        )
        lifecycleScope.launchWhenCreated {
            imageLoader.loadImageAsync(
                "cover.jpg".toAssetUri("app_images")
            ).collect {
                imageView.setImageBitmap(it)
            }
        }
    }

    private fun setUpNavigationView() {
        with(binding.navigationView) {
            itemIconTintList = if (context.isPhoneInDarkMode()) {
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.light_menu_icon_color
                    )
                )
            } else null
            itemTextColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    if (!context.isPhoneInDarkMode()) R.color.dark_menu_icon_color
                    else R.color.light_menu_icon_color
                )
            )
            setItemIconSize(52)
            setItemTextAppearance(R.style.MenuItemStyle)
        }
        configureNavigationMenu()
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun configureNavigationDrawer() {
        binding.drawerLayout.addDrawerListener(
            object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                }

                override fun onDrawerOpened(drawerView: View) {

                }

                override fun onDrawerClosed(drawerView: View) {
                    if (!fragmentSelected) return
                    fragmentSelected = false
                    when (fragmentId) {
                        AUTHOR_FRAGMENT -> setFragment(AuthorFragment())
                        CATEGORY_FRAGMENT -> setFragment(CategoryFragment())
                    }
                }

                override fun onDrawerStateChanged(newState: Int) {}
            }
        )
    }

    private fun configureNavigationMenu() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.authors -> {
                    fragmentSelected = true
                    fragmentId = if (fragmentId == AUTHOR_FRAGMENT) NO_FRAGMENT else {
                        dependency.apply { coroutineScope.vibrate(context) }
                        AUTHOR_FRAGMENT
                    }
                    closeDrawer()
                    true
                }
                R.id.categories -> {
                    fragmentSelected = true
                    fragmentId =
                        if (fragmentId == CATEGORY_FRAGMENT) NO_FRAGMENT else {
                            dependency.apply { coroutineScope.vibrate(context) }
                            CATEGORY_FRAGMENT
                        }
                    closeDrawer()
                    true
                }
                R.id.rate_us -> {
                    dependency.apply { coroutineScope.vibrate(context) }
                    rateApp()
                    true
                }
                R.id.share_app -> {
                    dependency.apply { coroutineScope.vibrate(context) }
                    share(
                        "${getString(R.string.app_share_message)}\n\nhttps://play.google.com/store/apps/details?id=${packageName}",
                        "Share"
                    )
                    true
                }
                R.id.more_apps -> {
                    dependency.apply { coroutineScope.vibrate(context) }
                    moreApps()
                    true
                }
                R.id.about -> {
                    dependency.apply { coroutineScope.vibrate(context) }
                    navigateTo(AboutActivity::class.java)
                    true
                }
                R.id.settings -> {
                    dependency.apply { coroutineScope.vibrate(context) }
                    navigateTo(SettingsActivity::class.java)
                    true
                }
                else -> false
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }
    }

    override fun setToolbarTitle(title: String) {
        binding.mainToolbar.title.text = title
    }

    override fun navigate(bundle: Bundle) {
        interstitialManager.showAd {
            navigateTo(ViewActivity::class.java, bundle = bundle)
        }
    }

    companion object {
        const val NO_FRAGMENT = "no_fragment"
        const val AUTHOR_FRAGMENT = "author_fragment"
        const val CATEGORY_FRAGMENT = "category_fragment"
    }
}