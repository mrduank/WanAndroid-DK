package com.example.dk.wanandroid

import android.Manifest
import android.os.Bundle
import com.example.baselib.base.IBasePresenter
import com.example.baselibrary.utils.PrefUtils
import com.example.dk.wanandroid.base.AppBaseActivity
import com.example.dk.wanandroid.constants.Constants
import com.example.dk.wanandroid.entity.IntegralEntity
import com.example.dk.wanandroid.http.HttpDefaultObserver
import com.example.dk.wanandroid.http.RetrofitHelper
import com.example.dk.wanandroid.proxy.IConfirmClickCallBack
import com.example.dk.wanandroid.ui.main.MainActivity
import com.example.dk.wanandroid.utils.DialogUtils
import com.example.wanandroid_dk.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
import java.util.concurrent.TimeUnit

class SplashActivity : AppBaseActivity<IBasePresenter<*>>(), PermissionCallbacks {


    companion object {
        private const val WRITE_EXTERNAL = 100
    }

    private var disposable: Disposable? = null
    private var perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val tips = "玩安卓现在要向您申请存储权限，用于存储历史记录以及保存小姐姐图片，您也可以在设置中手动开启或者取消。"

    override fun init(savedInstanceState: Bundle?) {
        saveIntegral()
        requestPermission()
    }

    private fun saveIntegral() {
        RetrofitHelper.getApiService().getIntegral()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<IntegralEntity>() {
                override fun disposable(d: Disposable) {

                }

                override fun onSuccess(t: IntegralEntity) {
                    PrefUtils.setObject(Constants.INTEGRAL_INFO, t)
                }

                override fun onError(errMsg: String) {

                }

            })
    }

    private fun requestPermission() {
        if (EasyPermissions.hasPermissions(this, *perms)) {
            startIntent()
        } else {
            DialogUtils.tips(this, tips, object : IConfirmClickCallBack {
                override fun onClick() {
                    requestLocationAndCallPermission()
                }

            })

        }
    }

    @AfterPermissionGranted(WRITE_EXTERNAL)
    private fun requestLocationAndCallPermission() {
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            startIntent()
        } else {
            EasyPermissions.requestPermissions(this, "请求权限写入", WRITE_EXTERNAL, *perms)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun startIntent() {
        disposable = Observable.timer(3000, TimeUnit.MILLISECONDS)
            .subscribe {
                intent(MainActivity::class.java, false)
                finish()
            }
    }

    override fun createPresenter(): IBasePresenter<*>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    /**
     * 权限申请失败
     */
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }


    /**
     * 权限申请成功
     */
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        startIntent()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
