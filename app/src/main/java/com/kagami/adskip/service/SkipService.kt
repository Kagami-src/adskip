package com.kagami.adskip.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.kagami.adskip.db.AppDatabase
import timber.log.Timber

class SkipService: AccessibilityService() {
    private lateinit var enableActivitySet:Set<String>
    override fun onServiceConnected() {
        super.onServiceConnected()
        val mutableSet= mutableSetOf<String>()
        serviceInfo=serviceInfo.apply {
            packageNames= AppDatabase.instance.adItemDao().findAllEnable().map {
                mutableSet.add(it.activityName)
                return@map it.packageName
            }.toTypedArray()
        }
        enableActivitySet=mutableSet.toSet()
    }
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
//        event?.packageName?.let {
//            Timber.e("kagamilog:${it}")
//        }
//        event?.className?.let {
//            Timber.e("kagamilog:${it}")
//        }
        if(enableActivitySet.contains(event?.className)) {
            val rootNode = rootInActiveWindow ?: return
            if (!isNotFind(rootNode, "跳过")) {
                findTxtClick(rootNode, "跳过")
            }
        }
    }

    override fun onInterrupt() {

    }
    fun isNotFind(rootNode: AccessibilityNodeInfo,txt:String):Boolean{
        val node=rootNode.findAccessibilityNodeInfosByText(txt)
        return node==null || node.isEmpty()
    }
    fun findTxtClick(rootNode: AccessibilityNodeInfo, txt:String){
        val node=rootNode.findAccessibilityNodeInfosByText(txt)
        if (node==null || node.isEmpty())
            return
        node.forEach {
            if(it.className=="android.widget.Button" || it.className=="android.widget.TextView") {
                it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }
    }
}