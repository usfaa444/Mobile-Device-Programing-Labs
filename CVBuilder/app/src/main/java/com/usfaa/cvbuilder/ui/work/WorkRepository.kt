package com.usfaa.cvbuilder.ui.work

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.usfaa.cvbuilder.data.models.Work
import javax.inject.Inject

class WorkRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : WorkRepositoryContract {

    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun getAllWork(typeKey: String): Collection<Work> {
        val workString = sharedPreferences.getString(typeKey, null)
        return if (workString != null) {
            gson.fromJson<MutableMap<Long, Work>?>(workString
                , object : TypeToken<MutableMap<Long, Work>>() {}.type).values
        } else mutableListOf()
    }

    override fun saveWork(typeKey: String, work: Work): Collection<Work> {
        val workMap = getWorMapData(typeKey)
        workMap[work.id] = work
        editor.putString(typeKey, gson.toJson(workMap)).apply()
        return workMap.values
    }

    override fun deleteWork(typeKey: String, work: Work): Collection<Work> {
        val workMap = getWorMapData(typeKey)
        workMap.remove(work.id)
        editor.putString(typeKey, gson.toJson(workMap)).apply()
        return workMap.values
    }

    private fun getWorMapData(typeKey: String) : MutableMap<Long, Work> {
        val workString = sharedPreferences.getString(typeKey, null)
        return if (workString != null)
            gson.fromJson(workString, object : TypeToken<MutableMap<Long, Work>>() {}.type)
        else mutableMapOf()
    }
}