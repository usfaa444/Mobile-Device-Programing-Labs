package com.usfaa.cvbuilder.ui.work

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.usfaa.cvbuilder.data.models.Work
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkViewModel @Inject constructor(
    private val repository: WorkRepositoryContract
) : ViewModel() {

    private val works = MutableLiveData<List<Work>>()
    private val projects = MutableLiveData<List<Work>>()
    private val message = MutableLiveData<String>()

    fun getWorks() : LiveData<List<Work>> = works
    fun getProjects() : LiveData<List<Work>> = projects
    fun getMessage() : LiveData<String> = message

    fun getAllWorks() {
        works.value = repository.getAllWork(WORK_KEY).toList()
    }

    fun saveWork(work: Work) {
        works.value = repository.saveWork(WORK_KEY, work).toList()
    }

    fun deleteWork(work: Work) {
        works.value = repository.deleteWork(WORK_KEY, work).toList()
    }

    fun getAllProjects() {
        projects.value = repository.getAllWork(PROJECT_KEY).toList()
    }

    fun saveProject(work: Work) {
        projects.value = repository.saveWork(PROJECT_KEY, work).toList()
    }

    fun deleteProject(work: Work) {
        projects.value = repository.deleteWork(PROJECT_KEY, work).toList()
    }

    companion object {
        const val WORK_KEY = "WORK_KEY"
        const val PROJECT_KEY = "PROJECT_KEY"
    }
}