package com.sarftec.dailymotivationalquotes.presentation.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sarftec.dailymotivationalquotes.application.imagestore.ImageStore
import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import com.sarftec.dailymotivationalquotes.presentation.listener.ActivityListener
import com.sarftec.dailymotivationalquotes.presentation.listener.FragmentListener
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

abstract class BaseFragment : Fragment(), FragmentListener {

    @Inject
    lateinit var repository: ApplicationRepository

    @Inject
    lateinit var imageStore: ImageStore

    protected val dependency by lazy {
        FragmentDependency(
            requireContext(),
            lifecycleScope,
            imageStore,
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
        val fragmentListener: FragmentListener
    )
}