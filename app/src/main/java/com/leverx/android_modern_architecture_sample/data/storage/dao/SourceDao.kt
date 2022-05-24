package com.leverx.android_modern_architecture_sample.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.leverx.android_modern_architecture_sample.data.storage.entity.SourceDB

@Dao
interface SourceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entities: List<SourceDB>)
}
