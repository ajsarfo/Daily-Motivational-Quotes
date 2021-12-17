package com.sarftec.dailymotivationalquotes.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.android.material.timepicker.MaterialTimePicker
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.application.advertisement.BannerManager
import com.sarftec.dailymotivationalquotes.application.file.*
import com.sarftec.dailymotivationalquotes.databinding.ActivitySettingsBinding
import com.sarftec.dailymotivationalquotes.presentation.binding.SettingsItemBinding
import com.sarftec.dailymotivationalquotes.presentation.notification.AlarmMaker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : BaseActivity() {

    @Inject
    lateinit var alarmMaker: AlarmMaker

    private val binding by lazy {
        ActivitySettingsBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun canShowInterstitial(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_settings),
            binding.mainBanner
        )
        /**********************************************************/
        binding.back.setOnClickListener {
            dependency.coroutineScope.vibrate(this)
            onBackPressed()
        }
        lifecycleScope.launchWhenCreated {
            configureShowNotifications()
            configureNotificationTime()
            configureVibration()
        }
    }

    private suspend fun configureShowNotifications() {
        val checkBox = binding.allowNotifications.settingsCheckbox
        fun onCheck() = lifecycleScope.launch {
            editSettings(ENABLE_NOTIFICATION, checkBox.isChecked)
            if (checkBox.isChecked) {
                toast("Notifications Allowed")
                alarmMaker.startAlarm()
            } else {
                toast("Notifications Disabled")
                alarmMaker.stopAlarm()
            }
        }
        binding.allowNotifications.binding = SettingsItemBinding(
            title = "Enable Notifications",
            subtitle = "Receive daily notifications at specific\ntime",
            checkEnabled = readSettings(ENABLE_NOTIFICATION, true).first(),
            onChecked = {
                onCheck()
            },
            onItem = {
                checkBox.isChecked = !checkBox.isChecked
                onCheck()
            }
        )
        binding.allowNotifications.executePendingBindings()
    }

    private suspend fun configureNotificationTime() {
        binding.notificationTime.binding = SettingsItemBinding(
            title = "Set Remainder Time",
            subtitle = convertMinuteToString(
                readSettings(NOTIFICATION_TIME, 8 * 60).first()
            ),
            checkVisibility = View.GONE,
            onChecked = {},
            onItem = {
                createNotificationTimePicker()
            }
        )
        binding.notificationTime.executePendingBindings()
    }

    private suspend fun configureVibration() {
        val checkBox = binding.allowVibration.settingsCheckbox
        fun onCheck() = lifecycleScope.launch {
            editSettings(ENABLE_VIBRATION, checkBox.isChecked)
            if (checkBox.isChecked) toast("Vibration Enabled")
            else toast("Vibration Disabled")
        }
        binding.allowVibration.binding = SettingsItemBinding(
            title = "Enable Vibration",
            subtitle = "Vibrate on key pressed",
            checkEnabled = readSettings(ENABLE_VIBRATION, true).first(),
            onChecked = {
                onCheck()
            },
            onItem = {
                checkBox.isChecked = !checkBox.isChecked
                onCheck()
            }
        )
        binding.allowVibration.executePendingBindings()
    }

    private fun createNotificationTimePicker() {
        val picker = Calendar.getInstance().let {
            MaterialTimePicker
                .Builder()
                .setHour(it.get(Calendar.HOUR_OF_DAY))
                .setMinute(it.get(Calendar.MINUTE))
                .setTitleText("Set Notification Time")
                .build()
        }

        picker.addOnPositiveButtonClickListener {
            val totalMinutes = 60 * picker.hour + picker.minute
            lifecycleScope.launch {
                binding.notificationTime
                    .settingsSubtitle
                    .text = convertMinuteToString(totalMinutes)
                editSettings(NOTIFICATION_TIME, totalMinutes)
                alarmMaker.startAlarm()
            }
        }
        picker.show(supportFragmentManager, picker.toString())
    }

    private fun convertMinuteToString(totalMinutes: Int): String {
        val hours = totalMinutes.div(60)
        val initial = if (hours >= 12) "PM" else "AM"
        val minutes = totalMinutes.rem(60)
        return "${hours.rem(12)} : ${String.format("%02d", minutes)} $initial"
    }
}