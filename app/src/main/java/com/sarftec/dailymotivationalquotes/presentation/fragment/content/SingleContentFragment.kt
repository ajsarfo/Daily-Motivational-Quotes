package com.sarftec.dailymotivationalquotes.presentation.fragment.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.databinding.FragmentSingleViewBinding
import com.sarftec.dailymotivationalquotes.presentation.binding.SingleContentBinding
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.SingleViewModel

class SingleContentFragment : BaseContentFragment() {

    private lateinit var binding: FragmentSingleViewBinding
    private lateinit var contentBinding: SingleContentBinding

    private val viewModel by viewModels<SingleViewModel>()

    private val quoteFadeInAnimation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.quote_fade_in).also {
            it.interpolator = AccelerateDecelerateInterpolator()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSingleViewBinding.inflate(
            inflater,
            container,
            false
        )
        lifecycleScope.launchWhenCreated {
            contentListener.getContentModels()?.let {
                viewModel.setModels(it)
                viewModel.next()
            }
        }
        viewModel.model.observe(viewLifecycleOwner) {
            if (!::contentBinding.isInitialized) {
                contentBinding = SingleContentBinding(
                    dependency = dependency,
                    contentListener = contentListener,
                    singleViewModel = viewModel,
                    model = it
                ).apply {
                    init()
                }
                binding.binding = contentBinding
            }
            contentBinding.changeModel(it)
            binding.run {
                contentMessage.startAnimation(quoteFadeInAnimation)
                contentSubtitle.startAnimation(quoteFadeInAnimation)
            }
            binding.executePendingBindings()
        }
        return binding.root
    }

    override fun navigate(bundle: Bundle) {
        activityListener.navigate(bundle)
    }
}