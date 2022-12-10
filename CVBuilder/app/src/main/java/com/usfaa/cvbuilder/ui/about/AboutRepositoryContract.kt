package com.usfaa.cvbuilder.ui.about

import com.usfaa.cvbuilder.data.models.AboutMeExtraData
import com.usfaa.cvbuilder.data.models.Education

interface AboutRepositoryContract {
    fun getAboutMe(): String
    fun saveAboutMe(data: String): String
    fun getAboutMeExtraData(): AboutMeExtraData
    fun saveEducation(education: Education) : Collection<Education>
    fun saveCertification(education: Education) : Collection<Education>
    fun deleteEducation(education: Education) : Collection<Education>
    fun deleteCertification(education: Education) : Collection<Education>
}