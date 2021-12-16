package com.sarftec.dailymotivationalquotes.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarftec.dailymotivationalquotes.application.Dependency
import com.sarftec.dailymotivationalquotes.databinding.ActivityFavoriteListBinding
import com.sarftec.dailymotivationalquotes.presentation.adapter.FavoriteListItemAdapter
import com.sarftec.dailymotivationalquotes.presentation.binding.FavoriteBinding
import com.sarftec.dailymotivationalquotes.presentation.tools.LoadingScreen
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.FavoriteViewModel
import kotlinx.coroutines.delay

class FavoriteListActivity : BaseActivity() {

    private val binding by lazy {
        ActivityFavoriteListBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    private val viewModel by viewModels<FavoriteViewModel>()

    private val loadingDialog by lazy {
        LoadingScreen(this)
    }

    private val favoriteItemAdapter by lazy {
        FavoriteListItemAdapter(
            FavoriteActivityContent(dependency) {
                navigate(it)
            },
            viewModel
        )
    }

    private val backgroundImage by lazy {
        imageStore.randomBackgroundImage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.fetch()
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.binding = FavoriteBinding(
            dependency,
            backgroundImage
        ).also {
            it.init()
        }
        binding.executePendingBindings()
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this@FavoriteListActivity)
            adapter = favoriteItemAdapter
        }
        loadingDialog.show()
        lifecycleScope.launchWhenCreated {
            delay(1000)
            loadingDialog.dismiss()
        }
        viewModel.model.observe(this as LifecycleOwner) {
            favoriteItemAdapter.submitData(it)
            if (it.isEmpty()) binding.noFavoriteMessage.visibility = View.VISIBLE
        }
    }


    private fun navigate(bundle: Bundle) {
        navigateTo(
            FavoriteQuoteActivity::class.java,
            bundle = bundle.apply {
                putString(FAVORITE_QUOTE_IMAGE, backgroundImage.toString())
            }
        )
    }

    class FavoriteActivityContent(
        val dependency: Dependency,
        val navigate: (Bundle) -> Unit
    )
}