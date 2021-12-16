package com.sarftec.dailymotivationalquotes.presentation.adapter

import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.dailymotivationalquotes.databinding.LayoutSeparatorItemBinding
import com.sarftec.dailymotivationalquotes.databinding.LayoutViewListItemBinding

class ViewListItemViewHolder(
    val holder: LinearLayout,
    val listItemBinding: LayoutViewListItemBinding? = null,
    val separatorItemBinding: LayoutSeparatorItemBinding? = null
) : RecyclerView.ViewHolder(holder)