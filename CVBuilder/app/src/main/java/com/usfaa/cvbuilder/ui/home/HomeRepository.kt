package com.usfaa.cvbuilder.ui.home

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.usfaa.cvbuilder.data.models.BasicInfo
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : HomeRepositoryContract {

    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun getAllHomeBasicInfo(): Collection<BasicInfo> {
        val basicInfo = sharedPreferences.getString(BASIC_INFO_KEY, null)
        return if (basicInfo != null) {
            gson.fromJson<MutableMap<String, BasicInfo>?>(basicInfo
                , object : TypeToken<MutableMap<String, BasicInfo>>() {}.type).values
        } else mutableListOf()
    }

    override fun addHomeBasicInfo(basicInfo: BasicInfo) : Collection<BasicInfo> {
        val basicInfoMap = getBasicInfoMapData()
        basicInfoMap[basicInfo.title] = basicInfo
        editor.putString(BASIC_INFO_KEY, gson.toJson(basicInfoMap)).apply()
        return basicInfoMap.values
    }

    override fun deleteBasicInfo(basicInfo: BasicInfo): Collection<BasicInfo> {
        val basicInfoMap = getBasicInfoMapData()
        basicInfoMap.remove(basicInfo.title)
        editor.putString(BASIC_INFO_KEY, gson.toJson(basicInfoMap)).apply()
        return basicInfoMap.values
    }

    private fun getBasicInfoMapData() : MutableMap<String, BasicInfo> {
        val basicInfoString = sharedPreferences.getString(BASIC_INFO_KEY, null)
        return if (basicInfoString != null)
            gson.fromJson(basicInfoString, object : TypeToken<MutableMap<String, BasicInfo>>() {}.type)
        else mutableMapOf()
    }

    companion object {
        const val BASIC_INFO_KEY = "BASIC_INFO_KEY"
    }
}