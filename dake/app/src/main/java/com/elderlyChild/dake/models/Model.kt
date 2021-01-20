package com.elderlyChild.dake.models

import android.os.Parcelable
import com.google.firebase.firestore.Exclude

abstract class Model{
    @Exclude
    var id: String? = null

    fun <T :Model?> withId(id: String?): T {
        this.id = id
        return this as T
    }
}