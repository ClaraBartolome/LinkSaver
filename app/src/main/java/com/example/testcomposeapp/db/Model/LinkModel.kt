package com.example.testcomposeapp.db.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "link_table")
data class LinkModel(
    @PrimaryKey(autoGenerate = true)var id: Int,
    @ColumnInfo (name = "name", defaultValue = "")var name: String = "",
    @ColumnInfo (name = "link", defaultValue = "")var link: String = "",
    @ColumnInfo (name = "dateOfCreation", defaultValue = "")var dateOfCreation: String = "",
    @ColumnInfo (name = "dateOfModified", defaultValue = "")var dateOfModified: String = "",
    @ColumnInfo (name = "folder", defaultValue = "")var folder: String?,
    @ColumnInfo (name = "isProtected", defaultValue = "0")var isProtected: Int = 0,
)