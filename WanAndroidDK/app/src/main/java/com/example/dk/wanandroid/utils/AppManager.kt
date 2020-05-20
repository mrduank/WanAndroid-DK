package com.example.dk.wanandroid.utils

import android.content.ClipboardManager
import android.content.Context
import com.example.baselib.utils.ToastUtils
import com.example.baselibrary.utils.PrefUtils
import com.example.dk.wanandroid.constants.Constants
import com.example.dk.wanandroid.event.LogoutEvent
import org.greenrobot.eventbus.EventBus

class AppManager {

    companion object {

        fun isLogin(): Boolean {
            return PrefUtils.getBoolean(Constants.LOGIN, false)
        }

        fun resetUser() {
            EventBus.getDefault().post(LogoutEvent())
            PrefUtils.setBoolean(Constants.LOGIN, false)
            PrefUtils.removeKey(Constants.USER_INFO)
        }

        fun copy(context: Context, msg: String) {
            var clip = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clip.text = msg
            ToastUtils.showToast(msg)
        }


    }
}