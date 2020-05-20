package com.example.dk.wanandroid.ui.login

import android.content.Context
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.example.baselib.utils.ToastUtils
import com.example.baselibrary.utils.PrefUtils
import com.example.dk.wanandroid.SplashActivity
import com.example.dk.wanandroid.base.AppBaseActivity
import com.example.dk.wanandroid.constants.Constants
import com.example.wanandroid_dk.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppBaseActivity<LoginContract.Presenter<LoginContract.View>>(),
    LoginContract.View, View.OnClickListener {

    /**
     * 是否显示明文
     */
    private var isPasswordShow = false


    override fun init(savedInstanceState: Bundle?) {
        llLogin.setOnClickListener(this)
        tvRegister.setOnClickListener(this)
        tvSkip.setOnClickListener(this)
        ivClear.setOnClickListener(this)
        ivPasswordVisibility.setOnClickListener(this)


    }

    override fun createPresenter(): LoginContract.Presenter<LoginContract.View>? {
        return LoginPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun loginSuccess() {
        PrefUtils.setBoolean(Constants.LOGIN, true)
        finish()
    }

    override fun getContext(): Context? {
        return this
    }

    override fun onError(error: String) {
        setViewStatus(true)
        ToastUtils.showToast(error)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.llLogin -> {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                when {
                    username.isEmpty() -> ToastUtils.showToast("请输入账号")
                    password.isEmpty() -> ToastUtils.showToast("请输入密码")
                    else -> {
                        setViewStatus(false)
                        presenter?.login(username, password)
                    }
                }
            }
            R.id.tvRegister -> intent(SplashActivity::class.java, false)
            R.id.tvSkip -> finish()
            R.id.ivClear -> {
                etUsername.requestFocus()
                etUsername.setText("")
            }
            R.id.ivPasswordVisibility -> {
                etPassword.requestFocus()
                etPassword.transformationMethod = if (isPasswordShow) {
                    isPasswordShow = false
                    ivPasswordVisibility.setImageResource(R.mipmap.password_show)
                    PasswordTransformationMethod.getInstance()
                } else {
                    isPasswordShow = true
                    //显示明文状态,手动将光标移到最后
                    ivPasswordVisibility.setImageResource(R.mipmap.password_hide)
                    HideReturnsTransformationMethod.getInstance()
                }
                etPassword.setSelection(etPassword.text.length)
            }
        }

    }

    /**
     * 登录时给具备点击事件的View上锁，登陆失败时解锁
     * 并且施加动画
     */
    private fun setViewStatus(lockStatus: Boolean) {
        llLogin.isEnabled = lockStatus
        tvRegister.isEnabled = lockStatus
        tvSkip.isEnabled = lockStatus
        etUsername.isEnabled = lockStatus
        etPassword.isEnabled = lockStatus
        if (lockStatus) {
            tvLoginTxt.visibility = View.VISIBLE
            indicatorView.visibility = View.GONE
            indicatorView.hide()
        } else {
            tvLoginTxt.visibility = View.GONE
            indicatorView.visibility = View.VISIBLE
            indicatorView.show()
        }
    }
}
