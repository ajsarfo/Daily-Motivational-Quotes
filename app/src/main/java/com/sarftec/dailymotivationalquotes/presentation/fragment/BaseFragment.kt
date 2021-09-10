package com.sarftec.dailymotivationalquotes.presentation.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sarftec.dailymotivationalquotes.application.imageloader.BitmapImageLoader
import com.sarftec.dailymotivationalquotes.application.imagestore.ImageStore
import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import com.sarftec.dailymotivationalquotes.presentation.listener.ActivityListener
import com.sarftec.dailymotivationalquotes.presentation.listener.FragmentListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment : Fragment(), FragmentListener {

    @Inject
    lateinit var repository: ApplicationRepository

    @Inject
    lateinit var imageStore: ImageStore

    @Inject
    lateinit var imageLoader: BitmapImageLoader


    protected val dependency by lazy {
        FragmentDependency(
            requireContext(),
            lifecycleScope,
            imageStore,
            imageLoader,
            this
        )
    }

    protected lateinit var activityListener: ActivityListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ActivityListener) activityListener = context
    }

    class FragmentDependency(
        val context: Context,
        val coroutineScope: CoroutineScope,
        val imageStore: ImageStore,
        val imageLoader: BitmapImageLoader,
        val fragmentListener: FragmentListener
    )
}