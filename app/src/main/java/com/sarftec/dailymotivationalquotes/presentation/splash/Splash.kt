package com.sarftec.dailymotivationalquotes.presentation.splash

import android.content.Context
import android.graphics.Color
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class Splash @Inject constructor(@ActivityContext private val context: Context) {

    private val quotes = listOf(
        "Life is what happens when you are busy making other plans" to "John Lennon",
        "The way to get started is to quit talking and begin doing" to "Walt Disney",
        "Always remember that you are absolutely unique. Just like everyone else" to "Margaret Mead",
        "Life is either a daring adventure or nothing at all" to "Hellen Keller",
        "The only impossible journey is the one you never begin" to "Tony Robbins",
        "You only live once, but if you do it right, once is enough" to "Mae West",
        "Success is walking from failure to failure with no loss of enthusiasm" to "Winston Churchill",
        "You miss 100% of the shots you don't take" to "Wayne Gretzky",
        "Believe you can and you're halfway there" to "Theodore Roosevelt",
        "Dream big and dare to fail" to "Norman Vaughan",
        "It does not matter how slowly you go as long as you do not stop" to "Confucius"
    )

    private val fonts = listOf(
        Font(context, "courgette_regular.ttf"),
        Font(context, "pts_sans_regular.ttf", null),
        Font(context, "oleoscript_bold.ttf", null),
        Font(context, "righteous_regular.ttf"),
        Font(context, "bad_script.ttf")
    )
    private val background = listOf(
        (0xFF0E766C).toInt() to Color.WHITE,
        (0xFF322514).toInt() to Color.WHITE,
        (0xFFF0A07C).toInt() to (0xFF5B0567).toInt(),
        (0xFF1C1C1C).toInt() to Color.WHITE,
        (0xFFe5e5dc).toInt() to Color.BLACK,
        (0xFF292826).toInt() to (0xFFF9D342).toInt(),
        (0xFF161B21).toInt() to (0xFFF4A950).toInt(),
        (0xFF080A52).toInt() to (0xFFED2188).toInt()
    )

    fun fetchSplashItem(): SplashItem {
        return SplashItem(quotes.random(), background.random(), fonts.random())
    }
}