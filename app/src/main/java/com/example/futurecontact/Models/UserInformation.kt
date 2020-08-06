package com.example.futurecontact.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserInformation(var uid:String, var name:String, var email:String, var phoneNumber:String) :Parcelable {
    constructor():this("","","","")
}