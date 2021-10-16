package com.anlian.ifilm.architecture

import com.anlian.ifilm.model.DefaultResponse

interface EditProfileView {
    fun binding()
    fun saveNewProfile()
    fun checkHardwareID()
    fun onUpdateSuccess(result: String)
    fun onUpdateFailure(message: String)
}