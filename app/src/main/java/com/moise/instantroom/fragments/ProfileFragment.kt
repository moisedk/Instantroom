package com.moise.instantroom.fragments

import android.util.Log
import com.moise.instantroom.Post
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment: HomeFragment() {
    override fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)

        query.include(Post.KEY_USER)
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.addAscendingOrder("createdAt")
        // This findInBackground returns all the post in the Post table, not just those associated with the current user
        query.findInBackground { posts, e ->
            if (e != null) Log.d(TAG, "done: Error fetching the posts") else if (posts != null) {
                for (post in posts) Log.d(
                    TAG, "Post: ${post.getDescription()} " +
                            "Username : ${post.getUser()?.username} " +
                            "Image: ${post.getImage()} " +
                            "ImageUrl: ${post.getImage()?.url} "
                )
                allPosts.addAll(posts)
                adapter.notifyDataSetChanged()
            }
        }    }
}