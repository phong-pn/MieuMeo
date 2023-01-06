package com.proxglobal.mieumeo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Duration
import java.util.*

@Parcelize
data class Le(
    var name: String = "",
    var price: Int = 5,
    var createAt: Long = Date().time,
    var expire: Long = 3600
): Parcelable