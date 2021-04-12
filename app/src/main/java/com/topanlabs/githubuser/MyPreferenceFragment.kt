package com.topanlabs.githubuser

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.topanlabs.githubuser.broadcast.AlarmReceiver

class MyPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var REMINDER_KEY: String
    private lateinit var reminderPreference: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        alarmReceiver = AlarmReceiver()
        init()
        setSummaries()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun init() {
        REMINDER_KEY = resources.getString(R.string.reminder_key)
        reminderPreference = findPreference<SwitchPreference>(REMINDER_KEY) as SwitchPreference

    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        reminderPreference.isChecked = sh!!.getBoolean(REMINDER_KEY, false)
    }

    override fun onSharedPreferenceChanged(sh: SharedPreferences?, key: String?) {
        if (key == REMINDER_KEY) {
            reminderPreference.isChecked = sh!!.getBoolean(REMINDER_KEY, false)
            val enabled = sh.getBoolean(REMINDER_KEY, false)
            if (enabled) {
                context?.let { alarmReceiver.setRepeatingAlarm(it, "Yuk dibuka aplikasinya!") }
            } else {
                context?.let { alarmReceiver.cancelAlarm(it) }
            }
        }
    }
}