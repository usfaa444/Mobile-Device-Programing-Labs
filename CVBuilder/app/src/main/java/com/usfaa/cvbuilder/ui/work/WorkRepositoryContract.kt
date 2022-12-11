package com.usfaa.cvbuilder.ui.work

import com.usfaa.cvbuilder.data.models.Work

interface WorkRepositoryContract {
    fun getAllWork(typeKey: String) : Collection<Work>
    fun saveWork(typeKey: String, work: Work) : Collection<Work>
    fun deleteWork(typeKey: String, work: Work) : Collection<Work>
}