package com.account.model.response

import com.account.domain.enum.Status

data class ApplicationUserResponse(
    val id: Int, val name: String,
    val email: String, val phoneNumber: String,
    val status: Status, val firmId: Int?,
    val firmName: String, val firmEmail: String,
    val firmPhoneNumber: String
)