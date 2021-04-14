package com.solace.productlistdemo

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

open class MyApplication() : Application()
{
    override fun onCreate() {
        super.onCreate()
        Realm.init(getApplicationContext())
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}