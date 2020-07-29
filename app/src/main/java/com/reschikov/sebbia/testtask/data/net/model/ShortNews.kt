package com.reschikov.sebbia.testtask.data.net.model

import android.os.Parcel
import android.os.Parcelable

private const val EMPTY_STRING = ""

data class ShortNews(val id : Long,
                     val title : String,
                     val date : String,
                     val shortDescription : String) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: EMPTY_STRING,
        parcel.readString() ?: EMPTY_STRING,
        parcel.readString() ?: EMPTY_STRING
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeLong(id)
            it.writeString(title)
            it.writeString(date)
            it.writeString(shortDescription)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ShortNews> {
        override fun createFromParcel(parcel: Parcel): ShortNews {
            return ShortNews(parcel)
        }

        override fun newArray(size: Int): Array<ShortNews?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ShortNews) return false
        return other.id == id && other.title == title && other.date == date &&
                other.shortDescription == shortDescription
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}