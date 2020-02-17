package com.dscvit.periodsapp.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.dscvit.periodsapp.R
import com.dscvit.periodsapp.model.registernotification.RegisterNotificationRequest
import com.dscvit.periodsapp.ui.auth.AuthViewModel
import com.dscvit.periodsapp.utils.Constants
import com.dscvit.periodsapp.model.Result
import com.dscvit.periodsapp.utils.PreferenceHelper
import com.dscvit.periodsapp.utils.PreferenceHelper.set
import kotlinx.android.synthetic.main.activity_post_auth.*
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import com.dscvit.periodsapp.utils.shortToast
import java.util.*

class PostAuthActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_auth)
        setUpNavigation()

        sharedPreferences = PreferenceHelper.customPrefs(applicationContext, Constants.PREF_NAME)

        if (sharedPreferences.getString(Constants.PREF_DEVICE_ID, null) == null) {
            val deviceId: String = UUID.randomUUID().toString()
            sharedPreferences[Constants.PREF_DEVICE_ID] = deviceId
            Log.d("esh", "Device ID: $deviceId")
        }

        if (!sharedPreferences.getBoolean(Constants.PREF_TOKEN_IS_UPDATED, false)) {
            val authViewModel by viewModel<AuthViewModel>()

            val deviceId = sharedPreferences.getString(Constants.PREF_DEVICE_ID, "")
            val fcmToken = sharedPreferences.getString(Constants.PREF_FCM_TOKEN, "")

            val registerNotificationRequest =
                RegisterNotificationRequest(deviceId = deviceId!!, registrationId = fcmToken!!)

            authViewModel.registerDevice(registerNotificationRequest).observe(this, Observer {
                when(it.status) {
                    Result.Status.LOADING -> {
                        shortToast("Loading")
                    }
                    Result.Status.SUCCESS -> {
                        if(it.data?.message == "New Device Registered") {
                            shortToast("Device Registered")
                            sharedPreferences[Constants.PREF_TOKEN_IS_UPDATED] = true
                        } else {
                            shortToast("Error in Registering device")
                        }
                    }
                    Result.Status.ERROR -> {
                        Log.d("esh", "Register Device ${it.message}")
                    }
                }
            })
        }

        onBackPressedDispatcher.addCallback(this) {}
    }

    private fun setUpNavigation() {
        NavigationUI.setupWithNavController(
            bottomNavigation,
            post_auth_nav_host.findNavController()
        )
    }
}
