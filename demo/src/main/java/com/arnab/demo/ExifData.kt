package com.arnab.demo

import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.IOException

fun setExifData(imageFile: File) {
    try {
        val exif = ExifInterface(imageFile.absolutePath)

        // Example metadata fields
        exif.setAttribute(ExifInterface.TAG_MAKE, "Google")
        exif.setAttribute(ExifInterface.TAG_MODEL, "Pixel 8")
        exif.setAttribute(ExifInterface.TAG_ARTIST, "Arnab")
        exif.setAttribute(ExifInterface.TAG_COPYRIGHT, "Arnab Kundu")
        exif.setAttribute(ExifInterface.TAG_DATETIME, "2025:10:07 10:45:00")
        exif.setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL.toString())

        // GPS coordinates (example: New York)
        exif.setGpsInfo(
            android.location.Location("dummy").apply {
                altitude = 20.0
                latitude = 22.6428587
                longitude = 88.4654711
                speed = 100.0F
            }
        )

        // Save changes
        exif.saveAttributes()
        println("✅ EXIF data saved successfully")

    } catch (e: IOException) {
        e.printStackTrace()
        println("❌ Failed to write EXIF data: ${e.message}")
    }
}


fun readExifData(imageFile: File) {
    val exif = ExifInterface(imageFile.absolutePath)
    val make = exif.getAttribute(ExifInterface.TAG_MAKE)
    val model = exif.getAttribute(ExifInterface.TAG_MODEL)
    val datetime = exif.getAttribute(ExifInterface.TAG_DATETIME)
    val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
    val gps = exif.getLatLong()

    println("📸 Make: $make")
    println("📱 Model: $model")
    println("⏰ DateTime: $datetime")
    println("↩️ Orientation: $orientation")
    println("⛳ GPS: ${gps?.get(0)}, ${gps?.get(1)}")
}
