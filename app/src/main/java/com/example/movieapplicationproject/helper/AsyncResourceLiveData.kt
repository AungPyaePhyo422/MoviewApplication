package com.example.movieapplicationproject.helper

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

public class AsyncResourceLiveData<T> : LiveData<AsyncViewResource<T>>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in AsyncViewResource<T>>) {

        if (hasActiveObservers()){
            Log.d("MainActivity", "sdfsfsdfs")
        }

        super.observe(owner, observer)
    }


    fun postLoading(){
        this.postValue(AsyncViewResource.Loading())
    }

    fun postSuccess(data : T){
        this.postValue(AsyncViewResource.Success(data))
    }

    fun postFail(exception : Throwable, error : String = ""){
        this.postValue(AsyncViewResource.Error(exception, error))
    }

}