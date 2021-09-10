package com.sarftec.dailymotivationalquotes.presentation.tools

import android.app.Dialog
import com.sarftec.dailymotivationalquotes.databinding.LayoutRatingsDialogBinding

class RatingsDialog(
    binding: LayoutRatingsDialogBinding
) : Dialog(binding.root.context) {

    var onNextTime: (() -> Unit)? = null
    var onReview : (() -> Unit)? = null

    init {
        setCancelable(false)
        setContentView(binding.root)
        binding.nextTime.setOnClickListener {
            onNextTime?.invoke()
            cancel()
        }
        binding.reviewCard.setOnClickListener {
            onReview?.invoke()
            cancel()
        }
    }
}