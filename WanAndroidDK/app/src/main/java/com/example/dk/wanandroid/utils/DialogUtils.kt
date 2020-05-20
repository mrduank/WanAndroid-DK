package com.example.dk.wanandroid.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.example.dk.wanandroid.proxy.IConfirmClickCallBack
import com.example.wanandroid_dk.R

class DialogUtils {

    companion object {
        private var dialog: Dialog? = null

        fun confirm(context: Context, tips: String, callbacks: IConfirmClickCallBack) {
            var dialog: Dialog? = null
            val bulider = AlertDialog.Builder(context)
            var view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null)
            var tvContent = view.findViewById<TextView>(R.id.tvContent)
            tvContent.text = tips
            bulider.setView(view)
                .setPositiveButton("确定") { dialog, which ->
                    callbacks?.onClick()
                    dialog.dismiss()
                }.setNegativeButton("取消") { dialog, which ->
                    dialog.dismiss()
                }
            dialog = bulider.create()
            dialog.show()

        }

        fun tips(context: Context, tips: String, callbacks: IConfirmClickCallBack) {
            var dialog: Dialog? = null
            val builder = AlertDialog.Builder(context)
            var view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null)
            var tvContent = view.findViewById<TextView>(R.id.tvContent)
            tvContent.text = tips
            builder.setView(view)
                .setPositiveButton("确定") { dialog, which ->
                callbacks?.onClick()
                dialog.dismiss()
            }.setCancelable(false)
            dialog = builder.create()
            dialog.show()

        }
    }
}