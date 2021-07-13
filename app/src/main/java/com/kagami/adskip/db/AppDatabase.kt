package com.kagami.adskip.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = arrayOf(
        DBAdItem::class
    ), version = 2, exportSchema = true
)
public abstract class AppDatabase : RoomDatabase() {
    private class MyDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { dataBase ->
                scope.launch {
                    dataBase.adItemDao().insertAll(defAds)
                }
            }
        }

        val defAds= listOf(
            DBAdItem().apply {
                appName="cc"
                packageName="com.netease.cc"
                activityName="com.netease.cc.appstart.CCMain"
            },
            DBAdItem().apply {
                appName="taobao"
                packageName="com.taobao.taobao"
                activityName="com.taobao.tao.TBMainActivity"
            },
            DBAdItem().apply {
                appName="bili"
                packageName="tv.danmaku.bili"
                activityName="tv.danmaku.bili.MainActivityV2"
            },
            DBAdItem().apply {
                appName="jd"
                packageName="com.jingdong.app.mall"
                activityName="com.jingdong.app.mall.MainFrameActivity"
            },
            DBAdItem().apply {
                appName="meituan"
                packageName="com.sankuai.meituan"
                activityName="com.meituan.android.pt.homepage.activity.MainActivity"
            },
            DBAdItem().apply {
                appName="ele"
                packageName="me.ele"
                activityName="me.ele.application.ui.splash.SplashActivity"
            },

        )
    }


    companion object {
        private var INSTANCE: AppDatabase? = null
        val instance: AppDatabase get() = INSTANCE!!
        fun setupDB(context: Context, scope: CoroutineScope) {
            val i = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "data"
            ).allowMainThreadQueries().run {
                addCallback(MyDatabaseCallback(scope))
                return@run build()
            }
            INSTANCE = i
        }
    }

    abstract fun adItemDao(): AdItemDAO
}