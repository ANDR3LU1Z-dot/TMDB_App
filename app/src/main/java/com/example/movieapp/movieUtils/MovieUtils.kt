package com.example.movieapp.movieUtils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.example.movieapp.R

class MovieUtils {
    companion object{
        private fun showErrorDialog(activity: Activity, context: Context){
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.title_error_message_dialog)
            builder.setMessage(R.string.text_error_message_dialog)
            builder.setPositiveButton(R.string.text_btn_error_message_dialog, DialogInterface.OnClickListener{ _, _ ->
                activity?.finish()
            } )
            builder.create()
            builder.show()
        }
    }


}