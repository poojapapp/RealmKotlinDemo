package com.solace.productlistdemo.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Employee() : RealmObject()  {
    companion object {
        val PROPERTY_NAME = "text_productName"
        const val PROPERTY_DESC = "text_desc"
        const val PROPERTY_CATEGORY = "text_category"
        const val PROPERTY_PRICE = "text_price"
        const val PROPERTY_MARKETPRICE = "text_marketPrice"
    }
 /*   val PROPERTY_NAME = "text_productName"
    val PROPERTY_DESC = "text_desc"
    val PROPERTY_CATEGORY = "text_category"
    val PROPERTY_PRICE = "text_price"
    val PROPERTY_MARKETPRICE = "text_marketPrice"*/

    @PrimaryKey
    var text_productName: String? = null
    var text_desc: String? = null
    var text_category: String? = null
    var text_price: String? = null
    var text_marketPrice: String? = null
}
