package com.moise.instantroom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(val context: Context, private val posts: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemView =  LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount() = posts.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        private val tvPostDescription: TextView = view.findViewById(R.id.tvPostDescription)
        private val ivProfilePicture: ImageView = view.findViewById(R.id.imageView)

        fun bind(post: Post){
            tvPostDescription.text = post.getDescription()
            tvUsername.text = post.getUser()?.username
            Glide.with(itemView.context)
                .load(post.getImage()?.url)
                .into(ivProfilePicture)

        }
    }
}