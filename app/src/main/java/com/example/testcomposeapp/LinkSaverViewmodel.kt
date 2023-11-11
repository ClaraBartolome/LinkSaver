package com.example.testcomposeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.testcomposeapp.db.LinkRepository
import com.example.testcomposeapp.db.Model.LinkModel
import kotlinx.coroutines.launch

class LinkSaverViewModel(private val repository: LinkRepository) : ViewModel() {

    val allLinks: LiveData<List<LinkModel>> = repository.allLinks

    fun insert(link:LinkModel)= viewModelScope.launch{
        repository.insert(link)
    }

    fun updateLink(link:LinkModel)= viewModelScope.launch{
        repository.updateLink(link)
    }

    fun getAllLinksByName() = viewModelScope.launch{
        repository.getAllLinksByName()
    }

    fun getAllLinksByDateOfCreation() = viewModelScope.launch{
        repository.getAllLinksByDateOfCreation()
    }

    fun getAllLinksByDateOfModified() = viewModelScope.launch{
        repository.getAllLinksByDateOfModified()
    }

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