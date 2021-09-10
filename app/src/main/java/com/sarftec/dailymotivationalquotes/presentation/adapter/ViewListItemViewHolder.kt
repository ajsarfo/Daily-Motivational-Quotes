package com.sarftec.dailymotivationalquotes.presentation.adapter

import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.dailymotivationalquotes.databinding.LayoutSeparatorItemBinding
import com.sarftec.dailymotivationalquotes.databinding.LayoutViewListItemBinding
import com.sarftec.dailymotivationalquotes.presentation.binding.ListItemContentBinding
import com.sarftec.dailymotivationalquotes.presentation.fragment.BaseFragment
import com.sarftec.dailymotivationalquotes.presentation.listener.ContentListener
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel

class ViewListItemViewHolder(
    val holder: LinearLayout,
    val listItemBinding: LayoutViewListItemBinding? = null,
    val separatorItemBinding: LayoutSeparatorItemBinding? = null
) : RecyclerView.ViewHolder(holder)