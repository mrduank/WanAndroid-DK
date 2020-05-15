package com.example.baselib.base

import android.content.Context

interface IBaseView {

    fun getContext(): Context?

    fun onError(error: String)
}