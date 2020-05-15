package com.example.baselib.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baselib.utils.ColorUtils
import com.example.baselib.utils.StatusUtils

abstract class BaseActivity<P : IBasePresenter<*>> : AppCompatActivity() {
    protected val TAG = javaClass.name
    protected var presenter: P? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutId = getLayoutId()
        if (layoutId != 0) {
            setContentView(layoutId)
        }
        presenter = createPresenter()
        presenter?.let { lifecycle.addObserver(it) }

        setStatusColor()
        setSystemInvadeBlack()
        init(savedInstanceState)


    }

    /**
     *设置状态栏背景颜色
     */
    protected fun setSystemInvadeBlack() {
        //第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtils.setSystemStatus(this, true, true)
    }

    /**
     * 设置沉浸式状态栏
     */
    protected fun setStatusColor() {
        StatusUtils.setUseStatusBarColor(this, ColorUtils.parseColor("#00ffffff"))
    }

    /**
     * 界面跳转
     */
    protected fun intent(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }

    /**
     * 携带bundle跳转
     */
    protected fun intent(bundle: Bundle, clazz: Class<*>) {
        startActivity(Intent(this, clazz).apply {
            putExtras(bundle)
        })

    }


    protected abstract fun init(savedInstanceState: Bundle?)
    protected abstract fun createPresenter(): P?
    protected abstract fun getLayoutId(): Int


}