package com.sarftec.dailymotivationalquotes.presentation.fragment.content

import android.content.Context
import com.sarftec.dailymotivationalquotes.presentation.fragment.BaseFragment
import com.sarftec.dailymotivationalquotes.presentation.listener.ContentListener

abstract class BaseContentFragment : BaseFragment() {

    protected lateinit var contentListener: ContentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ContentListener) contentListener = context
    }
}