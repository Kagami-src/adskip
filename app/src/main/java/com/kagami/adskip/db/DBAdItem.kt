package com.kagami.adskip.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dbaditem")
class DBAdItem {
    @PrimaryKey(autoGenerate = true)
    var id=0L
    var appName=""
    var packageName:String=""
    var activityName:String=""
    var enable:Boolean=true
}