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
    suspend fun getAllLinksByNameAsc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByNameAsc())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByNameDesc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByNameDesc())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByDateOfCreationAsc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByDateOfCreationAsc())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByDateOfCreationDesc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByDateOfCreationDesc())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByDateOfModifiedAsc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByDateOfModifiedAsc())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByDateOfModifiedDesc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByDateOfModifiedDesc())
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