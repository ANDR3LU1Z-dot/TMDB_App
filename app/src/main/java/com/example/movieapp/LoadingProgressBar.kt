package com.example.movieapp

import android.app.Activity
import android.app.AlertDialog

class LoadingProgressBar(private val mActivity: Activity) {
    private lateinit var isDialog: AlertDialog

    fun startLoading(){
        /*set View */
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_item, null)
        /* set Dialog */
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()

    }

    fun isDismiss(){
        isDialog.dismiss()
    }
}