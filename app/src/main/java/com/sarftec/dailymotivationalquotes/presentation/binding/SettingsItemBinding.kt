package com.sarftec.dailymotivationalquotes.presentation.binding

import android.view.View

class SettingsItemBinding(
    val title: String,
    val subtitle: String,
    val checkEnabled: Boolean = true,
    val checkVisibility: Int = View.VISIBLE,
    val onChecked: (() -> Unit),
    val onItem: (() -> Unit)
)