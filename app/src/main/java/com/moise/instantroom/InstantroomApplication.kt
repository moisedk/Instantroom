package com.moise.instantroom

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject

class InstantroomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // This registers the Post class at the very first time the application is run
        ParseObject.registerSubclass(Post::class.java)
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }

}