package com.anlian.ifilm.architecture

import com.anlian.ifilm.model.DataItem

interface MainView{
    fun adapterStart()
    fun insertData()
    fun showData(result: List<DataItem?>?)
    fun updateAdapter(result: List<DataItem?>?)
    fun moveToProfile()
    fun moveToSead()
    fun resultSuccess(result: List<DataItem?>?)
    fun resultFailed(throwable: Throwable)
}