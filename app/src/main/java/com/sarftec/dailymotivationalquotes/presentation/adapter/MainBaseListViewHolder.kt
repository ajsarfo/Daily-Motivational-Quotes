package com.sarftec.dailymotivationalquotes.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sarftec.dailymotivationalquotes.databinding.LayoutAuthorListBinding
import com.sarftec.dailymotivationalquotes.presentation.binding.BaseListBinding
import com.sarftec.dailymotivationalquotes.presentation.fragment.BaseFragment
import com.sarftec.dailymotivationalquotes.presentation.model.MainModel
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.BaseListViewModel

class MainBaseListViewHolder(
    private val binding: LayoutAuthorListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        dependency: BaseFragment.FragmentDependency,
        viewModel: BaseListViewModel,
        cache: MainBaseListAdapter.InitialColorCache,
        position: Int,
        mainModel: MainModel
    ) {
        binding.binding = BaseListBinding(
            dependency,
            viewModel,
            cache,
            position,
            mainModel
        ).also { it.init() }
        binding.executePendingBindings()
    }
}