package com.sarftec.dailymotivationalquotes.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.paging.PagingData
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.application.advertisement.BannerManager
import com.sarftec.dailymotivationalquotes.application.advertisement.RewardVideoManager
import com.sarftec.dailymotivationalquotes.application.file.vibrate
import com.sarftec.dailymotivationalquotes.databinding.ActivityViewBinding
import com.sarftec.dailymotivationalquotes.presentation.fragment.content.ListContentFragment
import com.sarftec.dailymotivationalquotes.presentation.fragment.content.SingleContentFragment
import com.sarftec.dailymotivationalquotes.presentation.listener.ContentListener
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel
import com.sarftec.dailymotivationalquotes.presentation.tools.LoadingScreen
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.ListViewModel
import kotlinx.coroutines.flow.Flow

class ViewActivity : BaseActivity(), ContentListener {

    private lateinit var tag: String

    private val binding by lazy {
        ActivityViewBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    private val viewModel by viewModels<ListViewModel>()

    private val rewardVideoManager by lazy {
        RewardVideoManager(
            this,
            R.string.admob_reward_video_id,
            adRequestBuilder,
            networkManager
        )
    }

    private val loadingScreen by lazy {
        LoadingScreen(this)
    }

    override fun canShowInterstitial(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_detail),
            binding.mainBanner
        )
        /**********************************************************/
        savedInstanceState ?: kotlin.run {
            viewModel.setBundle(intent.getBundleExtra(ACTIVITY_BUNDLE))
        }
        configureToolbar()
        setFragment(SingleContentFragment(), SINGLE_FRAGMENT)
    }

    private fun configureToolbar() {
        with(binding.viewToolbar) {
            title.text = viewModel.getToolbarTitle()
            back.setOnClickListener {
                dependency.apply {
                    coroutineScope.vibrate(context)
                }
                onBackPressed()
            }
            toListView.setOnClickListener {
                dependency.apply {
                    coroutineScope.vibrate(context)
                }
                if (tag == LIST_FRAGMENT) return@setOnClickListener
                setFragment(ListContentFragment(), LIST_FRAGMENT)
            }
            toPageView.setOnClickListener {
                dependency.apply {
                    coroutineScope.vibrate(context)
                }
                if (tag == SINGLE_FRAGMENT) return@setOnClickListener
                setFragment(SingleContentFragment(), SINGLE_FRAGMENT)
            }
        }
    }


    private fun setFragment(fragment: Fragment, id: String) {
        tag = id
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment, id)
            setReorderingAllowed(true)
        }
    }

    companion object {
        const val LIST_FRAGMENT = "list_fragment"
        const val SINGLE_FRAGMENT = "view_fragment"
    }

    override suspend fun getContentModels(): List<ContentModel.ListContentModel>? {
        return viewModel.fetch()
    }

    override suspend fun getContentModelPagingFlow(): Flow<PagingData<ContentModel>>? {
        return viewModel.getPagingFlow()
    }

    override fun getListViewModel(): ListViewModel {
        return viewModel
    }

    override fun save(contentModel: ContentModel) {
        viewModel.save(contentModel)
    }

    override fun showRewardVideo(callback: () -> Unit) {
        loadingScreen.show()
        rewardVideoManager.showRewardVideo {
            loadingScreen.dismiss()
            callback()
        }
    }
}