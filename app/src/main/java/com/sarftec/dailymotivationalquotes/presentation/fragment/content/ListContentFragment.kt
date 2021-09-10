package com.sarftec.dailymotivationalquotes.presentation.fragment.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.appodeal.ads.Appodeal
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.databinding.FragmentListViewBinding
import com.sarftec.dailymotivationalquotes.presentation.adapter.ViewListItemAdapter
import com.sarftec.dailymotivationalquotes.presentation.tools.LoadingScreen
import kotlinx.coroutines.flow.collect

class ListContentFragment : BaseContentFragment() {

    private lateinit var binding: FragmentListViewBinding

    private val listAdapter by lazy {
        ViewListItemAdapter(dependency, contentListener)
    }

    private val loadingScreen by lazy {
        LoadingScreen(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Show banner
        Appodeal.setBannerViewId(R.id.main_banner)
        contentListener.getListViewModel().createNativeManager(requireActivity())
        loadingScreen.show()
        binding = FragmentListViewBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        with(binding.recyclerLayout.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = listAdapter
        }
        lifecycleScope.launchWhenCreated {
            contentListener.getContentModelPagingFlow()?.collect {
                loadingScreen.dismiss()
                listAdapter.submitData(it)
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Appodeal.show(requireActivity(), Appodeal.BANNER_VIEW)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        contentListener.getListViewModel().destroyNativeManager()
    }

    override fun navigate(bundle: Bundle) {}
}