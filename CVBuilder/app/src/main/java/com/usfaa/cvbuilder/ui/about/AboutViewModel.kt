package com.usfaa.cvbuilder.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.usfaa.cvbuilder.data.models.Education
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val repository: AboutRepository
) : ViewModel() {
    private val messageLiveData = MutableLiveData<String>()
    private val educationsLiveData = MutableLiveData<List<Education>>()
    private val certificationsLiveData = MutableLiveData<List<Education>>()
    private val aboutMeLiveData = MutableLiveData<String>()

    fun getMessage() : LiveData<String> = messageLiveData
    fun getAboutMe() : LiveData<String> = aboutMeLiveData
    fun getEducations() : LiveData<List<Education>> = educationsLiveData
    fun getCertifications() : LiveData<List<Education>> = certificationsLiveData

    fun saveAboutMe(aboutMe: String) {
        aboutMeLiveData.value = repository.saveAboutMe(aboutMe)
    }

    fun getAboutMeData() {
        val aboutMeExtraData = repository.getAboutMeExtraData()
        educationsLiveData.value = aboutMeExtraData.education
        certificationsLiveData.value = aboutMeExtraData.certification
        aboutMeLiveData.value = repository.getAboutMe()
    }

    fun saveEducation(id: Long, institute: String, degree: String) {
        if (institute.isEmpty()) {
            messageLiveData.value = "Please provide the institute you attended this education"
            return
        }
        if (degree.isEmpty()) {
            messageLiveData.value = "Please provide the degree you achieved in this education"
            return
        }
        educationsLiveData.value = repository.saveEducation(Education(id, "image", institute, degree)).toList()
    }

    fun saveCertification(id: Long, institute: String, degree: String) {
        if (institute.isEmpty()) {
            messageLiveData.value = "Please provide the institute you attended this certification"
            return
        }
        if (degree.isEmpty()) {
            messageLiveData.value = "Please provide the degree you achieved in this certification"
            return
        }
        certificationsLiveData.value = repository.saveCertification(Education(id, "image", institute, degree)).toList()
    }

    fun deleteCertification(education: Education) {
        certificationsLiveData.value = repository.deleteCertification(education).toList()
    }

    fun deleteEducation(education: Education) {
        educationsLiveData.value = repository.deleteEducation(education).toList()
    }
}