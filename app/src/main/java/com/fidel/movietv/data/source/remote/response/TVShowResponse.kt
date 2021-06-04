package com.fidel.movietv.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TVShowResponse (
    var filmId: String,
    var title: String,
    var overview: String,
    var duration: String,
    var year: String,
    var image: String,
): Parcelable