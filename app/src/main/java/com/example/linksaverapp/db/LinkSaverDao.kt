package com.example.linksaverapp.db


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.linksaverapp.db.Model.LinkModel

@Dao
interface LinkSaverDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLink(link: LinkModel)

    @Update
    suspend fun updateLink(link: LinkModel)

    @Delete
    suspend fun deleteLink(link: LinkModel)

    @Query("DELETE FROM link_table")
    suspend fun deleteAll()

    @Query("DELETE FROM link_table WHERE folder = :folderName")
    suspend fun deleteAllinFolder(folderName: String)

    @Query("SELECT * FROM link_table ")
    fun getAll(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY name ASC")
    suspend fun getAllOrderedByNameAsc(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY name DESC")
    suspend fun getAllOrderedByNameDesc(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY dateOfCreation ASC")
    suspend fun getAllOrderedByDateOfCreationAsc(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY dateOfCreation DESC")
    suspend fun getAllOrderedByDateOfCreationDesc(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY dateOfModified ASC")
    suspend fun getAllOrderedByDateOfModifiedAsc(): List<LinkModel>

    @Query("SELECT * FROM link_table ORDER BY dateOfModified DESC")
    suspend fun getAllOrderedByDateOfModifiedDesc(): List<LinkModel>
}