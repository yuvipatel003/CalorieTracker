package com.appsdeviser.core.domain.model

import com.appsdeviser.core.utils.Constant.FEMALE
import com.appsdeviser.core.utils.Constant.MALE
import com.appsdeviser.core.utils.Constant.UNKNOWN

sealed class Gender(val name: String) {
    object Male : Gender(MALE)
    object Female : Gender(FEMALE)
    object Other : Gender(UNKNOWN)

    companion object {
        fun fromString(name: String): Gender {
            return when (name) {
                MALE -> Male
                FEMALE -> Female
                else -> Other
            }
        }
    }
}
