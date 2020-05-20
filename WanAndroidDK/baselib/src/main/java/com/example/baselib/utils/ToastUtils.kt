package com.example.baselib.utils

import android.widget.Toast
import com.example.baselib.BaseApplication

class ToastUtils {
    companion object {
        fun showToast(msg: String) {
            Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_LONG).show()
        }
    }

}