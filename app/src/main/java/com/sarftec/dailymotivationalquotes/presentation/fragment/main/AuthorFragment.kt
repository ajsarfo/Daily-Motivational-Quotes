package com.sarftec.dailymotivationalquotes.presentation.fragment.main

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.AuthorViewModel

class AuthorFragment : BaseListFragment() {

    override val viewModel by viewModels<AuthorViewModel>()

    override fun toolbarTitle() = "Author Quotes"

    override fun navigate(bundle: Bundle) {
        activityListener.navigate(bundle)
    }
}