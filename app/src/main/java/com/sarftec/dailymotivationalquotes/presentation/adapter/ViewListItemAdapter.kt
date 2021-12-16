package com.sarftec.dailymotivationalquotes.presentation.adapter

import android.animation.ObjectAnimator
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sarftec.dailymotivationalquotes.application.imagestore.ImageStore
import com.sarftec.dailymotivationalquotes.databinding.LayoutListContentHolderBinding
import com.sarftec.dailymotivationalquotes.databinding.LayoutSeparatorItemBinding
import com.sarftec.dailymotivationalquotes.databinding.LayoutViewListItemBinding
import com.sarftec.dailymotivationalquotes.presentation.binding.ListItemContentBinding
import com.sarftec.dailymotivationalquotes.presentation.fragment.BaseFragment
import com.sarftec.dailymotivationalquotes.presentation.listener.ContentListener
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel

class ViewListItemAdapter(
    private val dependency: BaseFragment.FragmentDependency,
    private val contentListener: ContentListener
) : PagingDataAdapter<ContentModel, ViewListItemViewHolder>(ListItemUtil) {

    private val imageCache = ImageCache(dependency.imageStore)

    private fun View.startAnimation() {
        ObjectAnimator.ofFloat(this, "translationY", 250f, 0f).apply {
            interpolator = LinearInterpolator()
            duration = 300
            start()
        }
    }

    override fun onBindViewHolder(viewHolder: ViewListItemViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ContentModel.ListContentModel -> viewHolder.listItemBinding?.let {
                viewHolder.holder.apply {
                    removeAllViews()
                    addView(it.root, 0)
                }
                it.binding = ListItemContentBinding(
                    dependency,
                    contentListener,
                    imageCache,
                    position,
                    item
                ).also { binding ->
                    binding.init()
                }
                it.apply {
                    contentMessage.startAnimation()
                    contentSubtitle.startAnimation()
                }
                it.executePendingBindings()
            }
            is ContentModel.SeparatorContentModel ->
                viewHolder.separatorItemBinding?.let {
                    it.separatorHolder.apply {
                        removeAllViews()
                      //  addView(item.nativeAd, 0)
                    }
                    viewHolder.holder.apply {
                        removeAllViews()
                        addView(it.root, 0)
                    }
                }
            else -> {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewListItemViewHolder {
        val parentView = LayoutListContentHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return if (viewType == LIST_ITEM) {
            val binding = LayoutViewListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ViewListItemViewHolder(holder = parentView.root, listItemBinding = binding)
        } else {
            val binding = LayoutSeparatorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ViewListItemViewHolder(holder = parentView.root, separatorItemBinding = binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ContentModel.ListContentModel -> LIST_ITEM
            is ContentModel.SeparatorContentModel -> SEPARATOR_ITEM
            else -> LIST_ITEM
        }
    }


    class ImageCache(private val imageStore: ImageStore) {

        private val imageMap = hashMapOf<Int, Uri>()

        fun get(index: Int): Uri {
            return imageMap.getOrPut(index) {
                imageStore.randomBackgroundImage()
            }
        }

        fun random(): Uri {
            return imageStore.randomBackgroundImage()
        }

        fun replace(index: Int, uri: Uri) {
            imageMap[index] = uri
        }
    }

    object ListItemUtil : DiffUtil.ItemCallback<ContentModel>() {

        override fun areItemsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
            return oldItem.id == oldItem.id
        }
    }

    companion object {
        const val SEPARATOR_ITEM = 0
        const val LIST_ITEM = 1
    }
}