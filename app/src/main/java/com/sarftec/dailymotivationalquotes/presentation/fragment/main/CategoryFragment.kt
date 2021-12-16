package com.sarftec.dailymotivationalquotes.presentation.fragment.main

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseListFragment() {

    override val viewModel by viewModels<CategoryViewModel>()

    override fun toolbarTitle(): String = "Category Quotes"

    override fun navigate(bundle: Bundle) {
        activityListener.navigate(bundle)
    }
}