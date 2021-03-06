package com.example.baselib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment<P : IBasePresenter<*>> : Fragment() {

    protected var TAG = javaClass.name
    protected var presenter: P? = null
    private var contentview: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        presenter?.let { lifecycle.addObserver(it) }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contentview = inflater.inflate(getLayoutId(), null)
        return contentview
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init(savedInstanceState)
    }


    protected abstract fun init(savedInstanceState: Bundle?)
    protected abstract fun createPresenter(): P
    protected abstract fun getLayoutId(): Int


}