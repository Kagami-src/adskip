package com.kagami.adskip.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kagami.adskip.db.AppDatabase
import com.kagami.adskip.db.DBAdItem

class AppListViewModel: ViewModel() {
    val appDataSet:LiveData<List<DBAdItem>>
    get() = AppDatabase.instance.adItemDao().findAll()
}