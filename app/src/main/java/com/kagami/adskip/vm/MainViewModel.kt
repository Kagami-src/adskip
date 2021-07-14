package com.kagami.adskip.vm

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kagami.adskip.service.ForegroundService
import com.kagami.adskip.service.SkipService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel: ViewModel(){
    private val _accessibilityState: MutableLiveData<Boolean> = MutableLiveData()
    val accessibilityState: LiveData<Boolean>
        get() = _accessibilityState

    private val _foregroundServiceState: MutableLiveData<Boolean> = MutableLiveData()
    val foregroundServiceState: LiveData<Boolean>
        get() = _foregroundServiceState

    fun reloadAccessibilityState(context:Context){
        viewModelScope.launch {
            _accessibilityState.value=checkAccessibilityState(context)
        }
    }
    fun reloadForegroundServiceState(context:Context){
        viewModelScope.launch {
            _foregroundServiceState.value=checkForegroundServiceState(context)
        }
    }
    private suspend fun checkAccessibilityState(context:Context)= withContext(Dispatchers.IO){
        val expectedComponentName = ComponentName(context, SkipService::class.java)

        val enabledServicesSetting = Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            ?: return@withContext false

        val colonSplitter = TextUtils.SimpleStringSplitter(':')
        colonSplitter.setString(enabledServicesSetting)

        while (colonSplitter.hasNext()) {
            val componentNameString = colonSplitter.next()
            val enabledService = ComponentName.unflattenFromString(componentNameString)

            if (enabledService != null && enabledService == expectedComponentName)
                return@withContext true
        }

        return@withContext false
    }

    private suspend fun checkForegroundServiceState(context:Context)= withContext(Dispatchers.IO){
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
            if (ForegroundService::class.java.name.equals(service.service.className)) {
                return@withContext true
            }
        }
        return@withContext false
    }
}