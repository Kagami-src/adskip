package com.kagami.adskip.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kagami.adskip.R
import com.kagami.adskip.databinding.MainActivityBinding
import com.kagami.adskip.service.ForegroundService
import com.kagami.adskip.ui.BaseViewBindingActivity
import com.kagami.adskip.vm.MainViewModel

class MainActivity: BaseViewBindingActivity<MainActivityBinding>() {
    override val bindingCreator: (LayoutInflater) -> MainActivityBinding
            = MainActivityBinding::inflate
    val viewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setSupportActionBar(toolBar)
            viewModel.accessibilityState.observe(this@MainActivity, Observer {
                accessibilityStateText.text = if(it) getString(R.string.enable) else getText(R.string.disable)
            })
            viewModel.foregroundServiceState.observe(this@MainActivity, Observer {
                foregroundServiceSwitch.isChecked=it
            })
            accessibilityButton.setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
            foregroundServiceSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                if(buttonView.isChecked==isChecked){
                    if(isChecked){
                        ForegroundService.start(this@MainActivity)
                    }else{
                        ForegroundService.stop(this@MainActivity)
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.reloadAccessibilityState(this)
        viewModel.reloadForegroundServiceState(this)
    }
}