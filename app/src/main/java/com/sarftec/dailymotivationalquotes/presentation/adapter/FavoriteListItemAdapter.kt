package com.sarftec.dailymotivationalquotes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.dailymotivationalquotes.databinding.LayoutFavoriteListItemBinding
import com.sarftec.dailymotivationalquotes.presentation.activity.FavoriteListActivity
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.FavoriteViewModel

class FavoriteListItemAdapter(
    private val activityContent: FavoriteListActivity.FavoriteActivityContent,
    private val viewModel: FavoriteViewModel,
    private var items: List<ContentModel.ListContentModel> = emptyList()
) : RecyclerView.Adapter<FavoriteListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListItemViewHolder {
        val binding = LayoutFavoriteListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteListItemViewHolder, position: Int) {
        holder.bind(activityContent, viewModel, items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitData(items: List<ContentModel.ListContentModel>) {
        this.items = items
        notifyDataSetChanged()
    }
}