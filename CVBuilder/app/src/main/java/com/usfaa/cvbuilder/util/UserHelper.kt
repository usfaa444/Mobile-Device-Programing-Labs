package com.usfaa.cvbuilder.util

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.usfaa.cvbuilder.data.models.User

class UserHelper constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    var user: User?
        get() {
            val userJson = sharedPreferences.getString(PREF_KEY_USER, null)
            return if (userJson != null) {
                gson.fromJson<User>(userJson, object : TypeToken<User>() {

                }.type)
            } else null
        }
        set(value) {
            editor.putString(PREF_KEY_USER, gson.toJson(value)).apply()
        }

    var registeredUser: User?
        get() {
            val userJson = sharedPreferences.getString(PREF_KEY_REGISTERED_USER, null)
            return if (userJson != null) {
                gson.fromJson<User>(userJson, object : TypeToken<User>() {}.type)
            } else null
        }
        private set(value) {
            editor.putString(PREF_KEY_REGISTERED_USER, gson.toJson(value)).apply()
        }

    fun isConnected(): Boolean = user != null

    fun logUserOut() {
        user = null
    }

    fun saveRegisteredUser() {
        registeredUser = User("John Doe", "john@gmail.com", "123456789")
    }

    private companion object {
        const val PREF_KEY_USER = "PREF_KEY_USER"
        const val PREF_KEY_REGISTERED_USER = "PREF_KEY_REGISTERED_USER"
    }
}