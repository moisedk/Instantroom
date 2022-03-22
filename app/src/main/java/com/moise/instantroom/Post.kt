package com.moise.instantroom

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser

// This class needs to be registered inside the Application class (InstantroomApplication)
// Every Post has a description, an image, and a user associated with it
// These three attributes can be gotten as from a post using the keys,
// which are the column names of the Post table on the Parse Dashboard
@ParseClassName ("Post")
class Post : ParseObject() {
    fun getDescription(): String? {
        return getString(KEY_DESCRIPTION)
    }
    fun setDescription(description: String){
        put(KEY_DESCRIPTION, description)
    }
    fun setImage(parseFile: ParseFile) {
        put(KEY_IMAGE, parseFile)
    }
    fun getImage(): ParseFile? {
        return getParseFile(KEY_IMAGE)
    }
    fun getUser(): ParseUser? {
        return getParseUser(KEY_DESCRIPTION)
    }
    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }

    companion object {
        const val KEY_DESCRIPTION = "description"
        const val KEY_IMAGE = "image"
        const val KEY_USER = "user"
    }
}