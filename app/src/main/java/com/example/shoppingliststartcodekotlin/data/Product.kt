package com.example.shoppingliststartcodekotlin.data

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(var name:String = "", var qty:Int = 0, @get:Exclude var id: String = "") : Parcelable {

    override fun toString(): String {
        return "Product: $name - Qty: $qty \n"
    }

}

