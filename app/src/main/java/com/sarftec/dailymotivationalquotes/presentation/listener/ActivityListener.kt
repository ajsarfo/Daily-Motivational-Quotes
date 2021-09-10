package com.sarftec.dailymotivationalquotes.presentation.listener

import android.os.Bundle

interface ActivityListener {
    fun setToolbarTitle(title: String)
    fun navigate(bundle: Bundle)
}