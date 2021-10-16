package com.anlian.ifilm.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileResponse(

	@field:SerializedName("profile")
	val profile: List<ProfileItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ProfileItem(

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("hardwareID")
	val hardwareID: String? = null,

	@field:SerializedName("Profile_Picture_Path")
	val profilePicturePath: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("Name")
	val name: String? = null,

	@field:SerializedName("Password")
	val password: String? = null
) : Parcelable
