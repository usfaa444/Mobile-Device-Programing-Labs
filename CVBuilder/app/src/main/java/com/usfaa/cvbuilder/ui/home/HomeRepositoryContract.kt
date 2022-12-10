package com.usfaa.cvbuilder.ui.home

import com.usfaa.cvbuilder.data.models.BasicInfo

interface HomeRepositoryContract {
    fun getAllHomeBasicInfo(): Collection<BasicInfo>
    fun addHomeBasicInfo(basicInfo: BasicInfo) : Collection<BasicInfo>
    fun deleteBasicInfo(basicInfo: BasicInfo) : Collection<BasicInfo>
}