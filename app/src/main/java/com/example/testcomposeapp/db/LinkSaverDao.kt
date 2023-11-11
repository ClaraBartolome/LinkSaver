package com.example.testcomposeapp.db


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testcomposeapp.db.Model.LinkModel

@Dao
interface LinkSaverDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLink(link: LinkModel)

    @Update
    fun updateLink(link: LinkModel)

    @Delete
    fun deleteLink(link: LinkModel)

    @Query("DELETE FROM link_table")
    suspend fun deleteAll()

    @Query("DELETE FROM link_table WHERE folder = :folderName")
    suspend fun deleteAllinFolder(folderName: String)

    @Query("SELECT * FROM link_table ")
    fun getAll(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY name ASC")
    suspend fun getAllOrderedByName(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY dateOfCreation ASC")
    fun getAllOrderedByDateOfCreation(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY dateOfModified ASC")
    fun getAllOrderedByDateOfModified(): List<LinkModel>

}