//package com.example.allinone.profile.domain.repository
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.net.Uri
//import com.example.allinone.profile.presentation.sealed.ProfileUploadState
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.UserProfileChangeRequest
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.StorageMetadata
//import com.google.firebase.storage.ktx.storage
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.flowOn
//import kotlinx.coroutines.tasks.await
//import kotlinx.coroutines.withContext
//import java.io.ByteArrayOutputStream
//
//class ProfileRepository(
//    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
//    private val storage: FirebaseStorage = Firebase.storage
//) {
//
//    fun uploadProfilePicture(
//        context: Context,
//        imageUri: Uri
//    ): Flow<ProfileUploadState> = flow {
//        emit(ProfileUploadState.Loading(0))
//
//        try {
//            val user = auth.currentUser ?: throw IllegalStateException("No user is signed in")
//
//            if (imageUri == Uri.EMPTY) {
//                throw IllegalArgumentException("No image selected")
//            }
//
//            val imageByteArray = withContext(Dispatchers.IO) {
//                convertImageUriToByteArray(
//                    context = context,
//                    imageUri
//                )
//            } ?: throw IllegalStateException("Failed to process image")
//
//
//            val storageRef = storage.reference.child("images/${user.uid}")
//
//            val metadata = StorageMetadata.Builder()
//                .setContentType("image/*")
//                .build()
//
//            val uploadTask = storageRef.putBytes(imageByteArray, metadata)
//
//            // Monitor for progress updates
//            var lastProgressUpdate = 0
//            uploadTask.addOnProgressListener { taskSnapshot ->
//                val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
//                if (progress - lastProgressUpdate >= 5 || progress == 100) {
//                    lastProgressUpdate = progress
//                    kotlinx.coroutines.runBlocking { emit(ProfileUploadState.Loading(progress)) }
//                }
//            }
//            uploadTask.await()
//
//            // Get download URL
//            val downloadUri = storageRef.downloadUrl.await()
//
//            val profileUpdates = UserProfileChangeRequest.Builder()
//                .setPhotoUri(downloadUri)
//                .build()
//
//            user.updateProfile(profileUpdates).await()
//            emit(ProfileUploadState.Success(downloadUri))
//
//        } catch (e: Exception) {
//            val errorMessage = when (e) {
//                is IllegalStateException,
//                is IllegalArgumentException -> e.message ?: "An error occurred"
//                else -> "Upload failed: ${e.message}"
//            }
//            emit(ProfileUploadState.Error(errorMessage))
//        }
//    }.flowOn(Dispatchers.IO)
//}
//
