package com.moise.instantroom

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.moise.instantroom.databinding.ActivityMainBinding
import com.parse.*
import java.io.File


class MainActivity : AppCompatActivity() {
    private val photoFileName = "photo.jpg"
    private var photoFile: File? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTakePicture.setOnClickListener{
            onLaunchCamera()
        }
        binding.btnPost.setOnClickListener{
            val user = ParseUser.getCurrentUser()
            val description = binding.etDescription.text.toString()
            if (photoFile != null)
                submitPost(description, user, photoFile!!)
            else
                Toast.makeText(this, "You need a nice pic to show the world what's happening!", Toast.LENGTH_SHORT).show()

        }

//        queryPosts()
    }

    override fun onDestroy() {
        super.onDestroy()
        ParseUser.logOutInBackground()
    }

    private fun submitPost(description: String, user: ParseUser, file: File) {
        val post = Post()
        post.setDescription(description)
        post.setUser(user)
        post.setImage(ParseFile(file))
        post.saveInBackground{exception ->
            if(exception != null){
                Log.d(TAG, "submitPost: Error while posting")
                exception.printStackTrace()
                Toast.makeText(this, "Error while making post", Toast.LENGTH_SHORT).show()
            }else{
                Log.d(TAG, "submitPost: Post successful")
                binding.etDescription.text = null
                binding.ivPicture.setImageDrawable(null)
            }
        }
    }


    private fun queryPosts() {
//        val query = ParseQuery.getQuery(Post::class.java)
        // We need to explicitly include the pointer to return it
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)

        query.include(Post.KEY_USER)
        // This findInBackground returns all the post in the Post table, not just those associated with the current user
        query.findInBackground { posts, e ->
            if (e != null) Log.d(TAG, "done: Error fetching the posts") else if (posts != null) {
                for (post in posts) Log.d(
                    TAG, "Post: ${post.getDescription()} " +
                            "Username : ${post.getUser()?.username}"
                )
            }
        }

    }
    private fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(this, "com.moise.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(packageManager) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    private fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                val ivPreview: ImageView = findViewById(R.id.ivPicture)
                ivPreview.setImageBitmap(takenImage)
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1
        const val TAG = "MainActivity"
    }
}