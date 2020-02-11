package com.dscvit.periodsapp.network

import com.dscvit.periodsapp.model.login.LoginRequest
import com.dscvit.periodsapp.model.login.LoginResponse
import com.dscvit.periodsapp.model.registernotification.RegisterNotificationRequest
import com.dscvit.periodsapp.model.registernotification.RegisterNotificationResponse
import com.dscvit.periodsapp.model.sendalert.SendAlertRequest
import com.dscvit.periodsapp.model.sendalert.SendAlertResponse
import com.dscvit.periodsapp.model.signup.SignupRequest
import com.dscvit.periodsapp.model.signup.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("signup")
    suspend fun signUpUser(@Body signupRequest: SignupRequest): Response<SignupResponse>

    @POST("login")
    suspend fun singInUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("device_register")
    suspend fun registerDevice(@Body registerNotificationRequest: RegisterNotificationRequest):
            Response<RegisterNotificationResponse>

    @POST("send_alert")
    suspend fun sendAlert(@Body sendAlertRequest: SendAlertRequest): Response<SendAlertResponse>

}