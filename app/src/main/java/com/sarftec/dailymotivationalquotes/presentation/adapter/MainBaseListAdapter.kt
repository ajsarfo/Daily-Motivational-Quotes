package com.sarftec.dailymotivationalquotes.presentation.adapter

import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.getOrDefault
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.dailymotivationalquotes.databinding.LayoutAuthorListBinding
import com.sarftec.dailymotivationalquotes.presentation.fragment.BaseFragment
import com.sarftec.dailymotivationalquotes.presentation.model.MainModel
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.BaseListViewModel
import java.util.*

class MainBaseListAdapter(
    private val dependency: BaseFragment.FragmentDependency,
    private val viewModel: BaseListViewModel,
    private var items: List<MainModel> = emptyList()
    ) : RecyclerView.Adapter<MainBaseListViewHolder>() {

    private val cache = InitialColorCache()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBaseListViewHolder {
        val binding = LayoutAuthorListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainBaseListViewHolder(binding)
    }

    override fun onBindViewHolder(holderMain: MainBaseListViewHolder, position: Int) {
        holderMain.bind(dependency,viewModel,cache, position, items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitData(items: List<MainModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    class InitialColorCache {
        private val initialMap = hashMapOf<String, String>()
        private val repColors = SparseIntArray()

        private val colorList = listOf(
            (0xFF4BA754).toInt(),
            (0xFFD86161).toInt(),
            (0xFFAA7AB2).toInt(),
            (0xFF8B47FF).toInt(),
            (0xFF322514).toInt(),
            (0xFFe0a96d).toInt(),
            (0xFFff9a8d).toInt(),
            (0xFF4a536b).toInt(),
            (0xFF1978a5).toInt(),
        )

        private fun computeInitial(text: String): String {
            val buffer = StringBuffer()
            text.split(" ").filter { it != "" }.let {
                if (it.size == 1) buffer.append(it.first().substring(0, 2))
                else {
                    buffer.append(it.first().first())
                    buffer.append(it.last().first())
                }
            }
            return buffer.toString().uppercase(Locale.ENGLISH)
        }

        fun getInitial(text: String): String {
            return initialMap.getOrPut(text) {
                computeInitial(text)
            }
        }

        fun getColor(position: Int): Int {
            var colorId = repColors.getOrDefault(position, -1)
            if (colorId == -1) {
                colorId = colorList.indices.random()
                repColors.put(position, colorId)
            }
            return colorList[colorId]
        }
    }
}