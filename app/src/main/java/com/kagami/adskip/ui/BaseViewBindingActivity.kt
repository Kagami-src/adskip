package com.kagami.adskip.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

open abstract class BaseViewBindingActivity<VB: ViewBinding>: AppCompatActivity() {
    protected lateinit var binding:VB
    protected abstract val bindingCreator:(LayoutInflater)->VB
    open override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=bindingCreator(layoutInflater)
        setContentView(binding.root)
        //findViewById<Toolbar>(R.id.my_toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}