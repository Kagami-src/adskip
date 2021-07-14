package com.kagami.adskip.db

import androidx.lifecycle.LiveData
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
    @Query("SELECT * FROM dbaditem")
    fun findAll(): LiveData<List<DBAdItem>>
}