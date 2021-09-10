package com.sarftec.dailymotivationalquotes.presentation.fragment.content

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appodeal.ads.Appodeal
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.presentation.fragment.BaseFragment
import com.sarftec.dailymotivationalquotes.presentation.listener.ContentListener

abstract class BaseContentFragment : BaseFragment() {

    protected lateinit var contentListener: ContentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ContentListener) contentListener = context
    }
}