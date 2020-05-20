package com.example.dk.wanandroid.ui.login

import com.example.baselib.base.IBasePresenter
import com.example.baselib.base.IBaseView

interface LoginContract {
    interface View : IBaseView {
        fun loginSuccess()
    }

    interface Presenter<T> : IBasePresenter<View> {
        fun login(username: String, password: String)
    }
}