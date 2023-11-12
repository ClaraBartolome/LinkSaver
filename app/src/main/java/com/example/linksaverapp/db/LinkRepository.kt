package com.example.linksaverapp.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.linksaverapp.db.Model.LinkModel

class LinkRepository (private val linkSaverDao: LinkSaverDao) {

    private val _allLinks = MutableLiveData<List<LinkModel>>()
    val allLinks: LiveData<List<LinkModel>>
        get() =_allLinks

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(link: LinkModel) {
        linkSaverDao.insertLink(link)
    }

    //region getAllLinks

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByName() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByName())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByDateOfCreation() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByDateOfCreation())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByDateOfModified() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByDateOfModified())
    }

    //endregion

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateLink(link: LinkModel) {
        linkSaverDao.updateLink(link)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteLink(link: LinkModel) {
        linkSaverDao.deleteLink(link)
    }

}