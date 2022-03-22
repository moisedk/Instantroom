package com.moise.instantroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        queryPost()
    }

    private fun queryPost() {
        val query = ParseQuery.getQuery(Post::class.java)
        // We need to explicitly include the pointer to return it
        query.include(Post.KEY_USER)
        // This findInBackground returns all the post in the Post table, not just those associated with the current user
        query.findInBackground { posts, e ->
            if (e != null) {
                Log.e(TAG, "done: Error fetching posts")
            } else {
                if (posts != null)
                    for (post in posts)
                        Log.d(TAG, "done returned post: ${post.getDescription()}, " +
                                "username: ${post.getUser()?.username} " +
                                "image: ${post.getImage()} "
                        )
            }
        }
    }
    companion object {
        const val TAG = "MainActivity"
    }
}