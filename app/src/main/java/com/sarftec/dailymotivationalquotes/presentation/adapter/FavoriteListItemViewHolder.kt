package com.sarftec.dailymotivationalquotes.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sarftec.dailymotivationalquotes.databinding.LayoutFavoriteListItemBinding
import com.sarftec.dailymotivationalquotes.presentation.activity.FavoriteListActivity
import com.sarftec.dailymotivationalquotes.presentation.binding.FavoriteListItemBinding
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.FavoriteViewModel

class FavoriteListItemViewHolder(
    val binding: LayoutFavoriteListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        dependency: FavoriteListActivity.FavoriteActivityContent,
        viewModel: FavoriteViewModel,
        model: ContentModel.ListContentModel
    ) {
        binding.binding = FavoriteListItemBinding(dependency, viewModel, model)
        binding.executePendingBindings()
    }
}