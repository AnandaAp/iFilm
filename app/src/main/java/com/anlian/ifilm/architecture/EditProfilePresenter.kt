package com.anlian.ifilm.architecture

import android.util.Log
import com.anlian.ifilm.api.RetrofitConnection
import com.anlian.ifilm.model.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "Presenter Response"
class EditProfilePresenter(private var view: EditProfileView) {
    fun senData(id: String, hardwareID: String,function: String){
        RetrofitConnection
            .getService()
            .addHardwareID(id,
                hardwareID,
                function)
            .enqueue(object: Callback<DefaultResponse>{
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    val result = response.body()?.message.toString()
                    view.onUpdateSuccess(result)
                    Log.d(TAG, "onSuccessResponse: $result")
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    val result = "Update Failure"
                    view.onUpdateFailure(result)
                    Log.d(TAG, "onFailure: $result")
                }
            })
    }
}