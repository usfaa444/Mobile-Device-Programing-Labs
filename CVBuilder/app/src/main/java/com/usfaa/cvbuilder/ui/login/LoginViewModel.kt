package com.usfaa.cvbuilder.ui.login

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.usfaa.cvbuilder.util.UserHelper
import com.usfaa.cvbuilder.R
import com.usfaa.cvbuilder.data.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val resources: Resources,
    private val userHelper: UserHelper
) : ViewModel() {

    private val error = MutableLiveData<String>()
    private val isLoading = MutableLiveData<Boolean>()
    private val user = MutableLiveData<User>()

    fun getErrorLiveData() : LiveData<String> = error
    fun getIsLoadingLiveData() : LiveData<Boolean> = isLoading
    fun getUserLiveData() : LiveData<User> = user

    fun validateUserInput(email: String, password: String) {
        if (email.isEmpty()) {
            error.value = resources.getString(R.string.fill_email)
            return
        }
        if (password.isEmpty()) {
            error.value = resources.getString(R.string.fill_password)
            return
        }
        checkUserCredentials(email, password)
    }

    private fun checkUserCredentials(email: String, password: String) {
        isLoading.value = true
        val registeredUser = userHelper.registeredUser
        registeredUser?.let { u ->
            if (u.email == email && u.password == password) {
                userHelper.user = u
                user.value = u
            } else {
                error.value = "No registered user found with the credentials you entered"
            }
        } ?: kotlin.run {
            error.value = "No registered user found with the credentials you entered"
        }
        isLoading.value = false
    }
}