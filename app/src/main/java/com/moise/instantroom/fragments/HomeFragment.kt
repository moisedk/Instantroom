package com.moise.instantroom.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moise.instantroom.MainActivity
import com.moise.instantroom.Post
import com.moise.instantroom.PostAdapter
import com.moise.instantroom.R
import com.parse.ParseQuery

open class HomeFragment : Fragment() {
    val allPosts = mutableListOf<Post>()
    lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val postRecyclerView =  view.findViewById<RecyclerView>(R.id.post_recyclerview)
        adapter = PostAdapter(requireContext(), allPosts)
        postRecyclerView.adapter = adapter
        postRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        queryPosts()
    }

    open fun queryPosts() {
//        val query = ParseQuery.getQuery(Post::class.java)
        // We need to explicitly include the pointer to return it
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)

        query.include(Post.KEY_USER)
        query.limit = 20
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
        }

    }
    companion object {
        const val TAG = "HomeFragment"
    }
}