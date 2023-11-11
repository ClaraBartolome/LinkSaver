package com.example.testcomposeapp.db

import android.app.Application

class LinksSaverApplication: Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { LinkRoomDatabase.getDatabase(this) }
    val repository by lazy { LinkRepository(database.linkSaverDao()) }
}