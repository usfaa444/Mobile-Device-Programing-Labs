package com.usfaa.cvbuilder.ui.about

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.usfaa.cvbuilder.data.models.AboutMeExtraData
import com.usfaa.cvbuilder.data.models.Education
import javax.inject.Inject

class AboutRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
): AboutRepositoryContract {

    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun getAboutMe(): String {
        return sharedPreferences.getString(ABOUT_ME_KEY, "Write a little about yourself here!")!!
    }

    override fun saveAboutMe(data: String): String {
        editor.putString(ABOUT_ME_KEY, data).apply()
        return data
    }

    override fun getAboutMeExtraData(): AboutMeExtraData {
        val educationsString = sharedPreferences.getString(EDUCATION_KEY, null)
        val educations = if (educationsString != null) {
            gson.fromJson<MutableMap<Long, Education>?>(educationsString
                , object : TypeToken<MutableMap<Long, Education>>() {}.type).values
        } else mutableListOf()

        val certificationsString = sharedPreferences.getString(CERTIFICATION_KEY, null)
        val certification = if (certificationsString != null) {
            gson.fromJson<MutableMap<Long, Education>?>(certificationsString
                , object : TypeToken<MutableMap<Long, Education>>() {}.type).values
        } else mutableListOf()
        return AboutMeExtraData(educations.toList(), certification.toList())
    }

    private fun getEducationOrCertification(key: String) : MutableMap<Long, Education> {
        val data = sharedPreferences.getString(key, null)
        return if (data != null)
            gson.fromJson(data, object : TypeToken<MutableMap<Long, Education>>() {}.type)
        else mutableMapOf()
    }

    override fun saveEducation(education: Education): Collection<Education> {
        val educationMap = getEducationOrCertification(EDUCATION_KEY)
        educationMap[education.id] = education
        editor.putString(EDUCATION_KEY, gson.toJson(educationMap)).apply()
        return educationMap.values
    }

    override fun saveCertification(education: Education): Collection<Education> {
        val certificationMap = getEducationOrCertification(CERTIFICATION_KEY)
        certificationMap[education.id] = education
        editor.putString(CERTIFICATION_KEY, gson.toJson(certificationMap)).apply()
        return certificationMap.values
    }

    override fun deleteEducation(education: Education): Collection<Education> {
        val educationMap = getEducationOrCertification(EDUCATION_KEY)
        educationMap.remove(education.id)
        editor.putString(EDUCATION_KEY, gson.toJson(educationMap)).apply()
        return educationMap.values
    }

    override fun deleteCertification(education: Education): Collection<Education> {
        val certificationMap = getEducationOrCertification(CERTIFICATION_KEY)
        certificationMap.remove(education.id)
        editor.putString(CERTIFICATION_KEY, gson.toJson(certificationMap)).apply()
        return certificationMap.values
    }

    companion object {
        const val ABOUT_ME_KEY = "ABOUT_ME_KEY"
        const val EDUCATION_KEY = "EDUCATION_KEY"
        const val CERTIFICATION_KEY = "CERTIFICATION_KEY"
    }
}