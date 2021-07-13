package com.kagami.adskip.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AdItemDAO {
    @Insert
    fun insertAll( items: List<DBAdItem>)

    @Query("SELECT * FROM dbaditem WHERE enable = :enable")
    fun findAllEnable(enable:Boolean=true): List<DBAdItem>
}