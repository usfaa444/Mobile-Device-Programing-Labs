package com.usfaa.cvbuilder.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.usfaa.cvbuilder.data.models.BasicInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepositoryContract
) : ViewModel() {
    private val homeBasicInfo = MutableLiveData<List<BasicInfo>>()
    private val message = MutableLiveData<String>()
    private val infoSaved = MutableLiveData<List<BasicInfo>>()

    fun getHomeBasicInfo() : LiveData<List<BasicInfo>> = homeBasicInfo
    fun getMessage() : LiveData<String> = message
    fun getInfoSaved() : LiveData<List<BasicInfo>> = infoSaved

    fun saveBasicInfo(title: String, info: String) {
        if (title.isEmpty()) {
            message.value = "Please fill the title!"
            return
        }
        if (info.isEmpty()) {
            message.value = "Please fill the message info"
            return
        }
        val savedData = repository.addHomeBasicInfo(BasicInfo(title, info))
        infoSaved.value = savedData.toMutableList()
    }

    fun getBasicInfo() {
        val savedData = repository.getAllHomeBasicInfo()
        homeBasicInfo.value = savedData.toMutableList()
    }

    fun deleteBasicInfo(basicInfo: BasicInfo) {
        val savedData = repository.deleteBasicInfo(basicInfo)
        infoSaved.value = savedData.toMutableList()
    }
}