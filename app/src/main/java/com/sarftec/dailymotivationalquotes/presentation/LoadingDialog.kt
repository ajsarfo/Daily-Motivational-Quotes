package com.sarftec.dailymotivationalquotes.presentation

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.sarftec.dailymotivationalquotes.databinding.LayoutLoadingBinding

class LoadingDialog(context: Context, inflater: LayoutInflater) : Dialog(context) {

    init {
        setContentView(LayoutLoadingBinding.inflate(inflater).root)
    }
}