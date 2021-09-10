package com.sarftec.dailymotivationalquotes.presentation.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarftec.dailymotivationalquotes.databinding.LayoutRecyclerBinding
import com.sarftec.dailymotivationalquotes.presentation.adapter.MainBaseListAdapter
import com.sarftec.dailymotivationalquotes.presentation.fragment.BaseFragment
import com.sarftec.dailymotivationalquotes.presentation.tools.LoadingScreen
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.BaseListViewModel

abstract class BaseListFragment : BaseFragment() {

    private lateinit var binding: LayoutRecyclerBinding

    protected abstract val viewModel: BaseListViewModel

    private val baseListAdapter by lazy {
        MainBaseListAdapter(dependency, viewModel)
    }

    private val loadingScreen by lazy {
        LoadingScreen(requireContext())
    }

    protected abstract fun toolbarTitle() : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        savedInstanceState ?: loadingScreen.show()
        activityListener.setToolbarTitle(toolbarTitle())
        binding = LayoutRecyclerBinding.inflate(
            LayoutInflater.from(requireContext()),
            null,
            false
        )
        setUpRecyclerView()
        viewModel.fetch()
        viewModel.models.observe(viewLifecycleOwner) {
            baseListAdapter.submitData(it)
            loadingScreen.dismiss()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    private fun setUpRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = baseListAdapter
    }
}