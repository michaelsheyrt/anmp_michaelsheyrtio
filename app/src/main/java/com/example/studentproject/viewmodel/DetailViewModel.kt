package com.example.studentproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.studentproject.model.Student
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val studentLD = MutableLiveData<Student>()
    val errorLD = MutableLiveData<Boolean>()
    val TAG:String = "volley Tag"
    var queue: RequestQueue?=null

    fun fetch(id: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/LLMW"
        errorLD.value = false

        val stringRequest = StringRequest(Request.Method.GET, url,
            {
                val sType = object: TypeToken<List<Student>>(){}.type
                val result = Gson().fromJson<List<Student>>(it, sType) as ArrayList
                val student = result.find { it.id == id } as Student
                studentLD.value = student

            },
            {
                Log.d("volley_status", it.message.toString())
                errorLD.value = true
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}