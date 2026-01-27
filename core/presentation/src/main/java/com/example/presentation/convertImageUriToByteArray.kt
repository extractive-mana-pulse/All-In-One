package com.example.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream

fun convertImageUriToByteArray(context: Context, imageUri: Uri): ByteArray? {
        return try {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(imageUri) ?: return null

            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

            outputStream.toByteArray()
        } catch (e: Exception) {
            null
        }
    }