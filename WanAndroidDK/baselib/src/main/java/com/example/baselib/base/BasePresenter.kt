package com.example.baselib.base

import io.reactivex.disposables.CompositeDisposable


class BasePresenter<V : IBaseView>(view: V) : IBasePresenter<V> {

    protected var view: V? = view

    private var compositeDisposable: CompositeDisposable? = null

    init {
        compositeDisposable = CompositeDisposable()
    }


    override fun onCreate() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onDestroy() {
        compositeDisposable?.clear()
    }

    protected fun addSubscribe(disposable: CompositeDisposable) {
        compositeDisposable?.add(disposable)
    }
}