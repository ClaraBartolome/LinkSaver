package com.example.linksaverapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.linksaverapp.db.LinkRepository
import com.example.linksaverapp.db.Model.LinkModel
import kotlinx.coroutines.launch

class LinkSaverViewModel(private val repository: LinkRepository) : ViewModel() {

    val allLinks: LiveData<List<LinkModel>> = repository.allLinks

    fun insert(link:LinkModel)= viewModelScope.launch{
        repository.insert(link)
        getAllLinksByNameAsc()
    }

    fun deleteLink(link:LinkModel)= viewModelScope.launch{
        repository.deleteLink(link)
    }

    fun deleteListLink(linkList: List<LinkModel>)= viewModelScope.launch{
        repository.deleteListLink(linkList = linkList)
    }

    fun updateLink(link:LinkModel)= viewModelScope.launch{
        repository.updateLink(link)
    }

    //region ORDER
    fun getAllLinksByNameAsc() = viewModelScope.launch{
        repository.getAllLinksByNameAsc()
    }

    fun getAllLinksByNameDesc() = viewModelScope.launch{
        repository.getAllLinksByNameDesc()
    }

    fun getAllLinksByDateOfCreationAsc() = viewModelScope.launch{
        repository.getAllLinksByDateOfCreationAsc()
    }

    fun getAllLinksByDateOfCreationDesc() = viewModelScope.launch{
        repository.getAllLinksByDateOfCreationDesc()
    }

    fun getAllLinksByDateOfModifiedAsc() = viewModelScope.launch{
        repository.getAllLinksByDateOfModifiedAsc()
    }

    fun getAllLinksByDateOfModifiedDesc() = viewModelScope.launch{
        repository.getAllLinksByDateOfModifiedDesc()
    }

    //endregion

}

class LinkSaverViewModelFactory(private val repository: LinkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LinkSaverViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LinkSaverViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}