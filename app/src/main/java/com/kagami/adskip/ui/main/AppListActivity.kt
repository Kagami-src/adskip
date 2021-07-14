package com.kagami.adskip.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kagami.adskip.R
import com.kagami.adskip.databinding.ActivityAppListBinding
import com.kagami.adskip.ui.BaseViewBindingActivity
import com.kagami.adskip.ui.adapter.AppListAdapter
import com.kagami.adskip.vm.AppListViewModel
import com.kagami.adskip.vm.MainViewModel

class AppListActivity : BaseViewBindingActivity<ActivityAppListBinding>() {
    override val bindingCreator: (LayoutInflater) -> ActivityAppListBinding
            = ActivityAppListBinding::inflate
    val viewModel: AppListViewModel by viewModels()
    val adapter=AppListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setSupportActionBar(toolBar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setTitle(R.string.title_applist)

            recyclerView.adapter=adapter
            viewModel.appDataSet.observe(this@AppListActivity, Observer {
                adapter.submitList(it)
            })
        }
    }
}