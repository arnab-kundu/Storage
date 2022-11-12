package com.arnab.storage

import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build.VERSION_CODES
import android.util.Log
import androidx.annotation.RequiresApi
import com.arnab.storage.FileLocationCategory.CACHE_DIRECTORY
import com.arnab.storage.FileLocationCategory.DATA_DIRECTORY
import com.arnab.storage.FileLocationCategory.DOCUMENT_DIRECTORY
import com.arnab.storage.FileLocationCategory.DOWNLOADS_DIRECTORY
import com.arnab.storage.FileLocationCategory.EXTERNAL_CACHE_DIRECTORY
import com.arnab.storage.FileLocationCategory.EXTERNAL_FILES_DIRECTORY
import com.arnab.storage.FileLocationCategory.FILES_DIRECTORY
import com.arnab.storage.FileLocationCategory.MEDIA_DIRECTORY
import com.arnab.storage.FileLocationCategory.MUSIC_DIRECTORY
import com.arnab.storage.FileLocationCategory.OBB_DIRECTORY
import com.arnab.storage.FileLocationCategory.PICTURES_DIRECTORY
import com.arnab.storage.FileLocationCategory.VIDEOS_DIRECTORY
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream


@Suppress("RedundantExplicitType")
class AppFileManager(packageName: String) : FileManager, ZipManager, EncryptionManager() {

    private val APPLICATION_ID: String = packageName


    override fun createFolder(folderName: String, path: String): Boolean {
        val rootFolder: File = File(path, folderName)
        return rootFolder.mkdirs()
    }

    override fun createAppsInternalPrivateStoragePath(path: String): File? {

        try {
            val rootFolderPath = "/storage/emulated/0/Android"
            var folder: File = File(rootFolderPath)

            val pathFoldersList: List<String> = path.split("/")
            pathFoldersList.forEach { childFolder ->
                folder = File(folder, childFolder)

                if (!folder.exists()) {
                    Logg.d("Is Folder Created at: ${folder.absolutePath}: ${folder.mkdirs()}")
                }
            }

            return folder

        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    override fun createSharedStoragePath(path: String): File? {
        TODO("Not yet implemented")
    }

    override fun renameFolder(folderPath: String, newFolderName: String) {
        TODO("Not yet implemented")
    }

    override fun deleteFolder(directory: File) {
        for (file in directory.listFiles()) {
            if (!file.isDirectory) {
                file.delete()
            }
        }
    }

    @Suppress("unused", "UNUSED_PARAMETER", "UNUSED_ANONYMOUS_PARAMETER")
    private fun createMediaFile(context: Context, filePath: String, fileName: String, fileExtension: String?): File {

        /** Create path */
        val folder = createAppsInternalPrivateStoragePath("media/${APPLICATION_ID}")

        val file: File = if (fileExtension == null) File(folder!!.path, fileName)
        else File(folder!!.path, "$fileName.$fileExtension")

        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        // Without MediaScan file not visible to in PC after connecting via USB
        MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null) { path, uri ->

        }
        return file
    }

    @RequiresApi(VERSION_CODES.N)
    override fun createFile(context: Context, fileLocationCategory: FileLocationCategory, fileName: String, fileExtension: String?): File {

        /** Create path */
        val folder: File? = when(fileLocationCategory) {
            CACHE_DIRECTORY          -> context.cacheDir
            DATA_DIRECTORY           -> context.dataDir
            FILES_DIRECTORY          -> context.filesDir
            EXTERNAL_CACHE_DIRECTORY -> context.externalCacheDir.let { if (it == null) Logg.w("externalCacheDir returns null"); it }

            EXTERNAL_FILES_DIRECTORY -> context.getExternalFilesDir(null).let { if (it == null) Logg.w("getExternalFilesDir returns null"); it }
            MEDIA_DIRECTORY          -> createAppsInternalPrivateStoragePath("media/${APPLICATION_ID}").let { if (it == null) Logg.w("createMediaDir returns null"); it }
            OBB_DIRECTORY            -> context.obbDir

            DOWNLOADS_DIRECTORY      -> File("/storage/emulated/0/Download/")
            DOCUMENT_DIRECTORY       -> TODO()
            MUSIC_DIRECTORY          -> TODO()
            PICTURES_DIRECTORY       -> TODO()
            VIDEOS_DIRECTORY         -> TODO()
        }

        val file: File = if (fileExtension == null) File(folder!!.path, fileName)
        else File(folder!!.path, "$fileName.$fileExtension")

        if (!file.exists()) {
            try {
                file.createNewFile()
                Logg.v("Created file successfully")
            } catch (e: IOException) {
                e.printStackTrace()
                Logg.e("Failed to create file: $e")
            }
        }
        // Without MediaScan file not visible to in PC after connecting via USB
        MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null) { path, uri ->
            Logg.v("Created file path: $path")
            Logg.v("Created file uri: $uri")
        }
        return file
    }

    @Throws(IOException::class)
    override fun copyFile(sourcePath: String, destinationPath: String): Boolean {
        val inputStream: InputStream = FileInputStream(sourcePath)
        try {
            val outputStream: OutputStream = FileOutputStream(destinationPath)
            try {
                // Transfer bytes from in to out
                val buffer = ByteArray(1024)
                var len: Int
                while (inputStream.read(buffer).also { len = it } > 0) {
                    outputStream.write(buffer, 0, len)
                }
            } catch (e: Exception) {
                Logg.e(e.toString())
                return false
            } finally {
                outputStream.close()
            }
            return true
        } catch (e: FileNotFoundException) {
            Logg.e(e.toString())
            return false
        } finally {
            inputStream.close()
        }
    }

    override fun deleteFile(sourceFilePath: String): Boolean {
        return File(sourceFilePath).delete()
    }

    override fun moveFile(sourcePath: String, destinationPath: String) {
        copyFile(sourcePath, destinationPath)
        deleteFile(sourcePath)
    }

    @RequiresApi(VERSION_CODES.N)
    override fun renameFile(context: Context, existingFilePath: String, newFileName: String): File {
        val newFile = createFile(context = context, EXTERNAL_FILES_DIRECTORY, fileName = newFileName, fileExtension = null)
        val existingFile = File(existingFilePath)
        val existingFileInputStream: InputStream = FileInputStream(existingFile)
        copyInputStreamToFile(inputStream = existingFileInputStream, file = newFile)
        deleteFile(existingFilePath)
        return newFile
    }

    @Throws(IOException::class)
    override fun copyInputStreamToFile(inputStream: InputStream, file: File) {

        FileOutputStream(file, false).use { outputStream ->
            var read: Int
            val bytes = ByteArray(DEFAULT_BUFFER_SIZE)
            while (inputStream.read(bytes).also { read = it } != -1) {
                outputStream.write(bytes, 0, read)
            }
        }
    }

    /**
     * Zip all the files available in provided path
     */
    override fun zipFiles(srcFolderPath: String, destZipFilePath: String): File {
        var zip: ZipOutputStream? = null
        var fileWriter: FileOutputStream? = null
        fileWriter = FileOutputStream(destZipFilePath)
        zip = ZipOutputStream(fileWriter)
        addFolderToZip("", srcFolderPath, zip)
        zip.flush()
        zip.close()
        return File(destZipFilePath)
    }

    /**
     * Zip only provide list of files
     */
    override fun zipListOfFiles(files: ArrayList<String>, zipFileName: String) {
        val bufferSize = 1024
        try {
            var origin: BufferedInputStream?
            val dest = FileOutputStream(zipFileName)
            val out: ZipOutputStream = ZipOutputStream(
                BufferedOutputStream(dest)
            )
            val data: ByteArray = ByteArray(bufferSize)
            for (i in files.indices) {
                Log.v("Compress", "Adding: " + files[i])
                val fi: FileInputStream = FileInputStream(files[i])
                origin = BufferedInputStream(fi, bufferSize)
                val entry: ZipEntry = ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1))
                out.putNextEntry(entry)
                var count: Int
                while (origin.read(data, 0, bufferSize).also { count = it } != -1) {
                    out.write(data, 0, count)
                }
                origin.close()
            }
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Improved in terms of performance. Unzipping time is very less compare to unZipFileSlowly()
     * The reason of preforming fast is its using **BufferedOutputStream** instead of **FileOutputStream**
     * @see     com.akundu.kkplayer.storage.AppFileManager.unZipFileSlowly
     * @see     java.io.BufferedOutputStream
     */
    override fun unZipFile(zipFilePath: String, extractLocationPath: String) {
        try {
            val inputStream: FileInputStream = FileInputStream(zipFilePath)
            val zipStream = ZipInputStream(inputStream)
            var zEntry: ZipEntry?
            while (zipStream.nextEntry.also { zEntry = it } != null) {
                Log.d("Unzip", "Unzipping " + zEntry!!.name + " at " + extractLocationPath)
                if (zEntry!!.isDirectory) {
                    createFolder(folderName = zEntry!!.name, path = extractLocationPath)
                } else {
                    val fout: FileOutputStream = FileOutputStream(extractLocationPath + "/" + zEntry!!.name)
                    val bufout = BufferedOutputStream(fout)
                    val buffer = ByteArray(1024)
                    var read: Int
                    while (zipStream.read(buffer).also { read = it } != -1) {
                        bufout.write(buffer, 0, read)
                    }
                    zipStream.closeEntry()
                    bufout.close()
                    fout.close()
                }
            }
            zipStream.close()
            Log.d("Unzip", "Unzipping complete. path :  $extractLocationPath")
        } catch (e: java.lang.Exception) {
            Log.d("Unzip", "Unzipping failed")
            e.printStackTrace()
        }

    }

    /**
     * This unzip process is comparatively slower. For faster unzipping process use **unZipFile()**
     * The reason of preforming slow is its using **FileOutputStream** instead of **BufferedOutputStream**
     * @see     com.akundu.kkplayer.storage.AppFileManager.unZipFile
     */
    @Deprecated(
        message = "Use unZipFile() for fast unzipping process",
        replaceWith = ReplaceWith("unZipFile(zipFilePath = , extractLocationPath = )"),
        level = DeprecationLevel.WARNING
    )
    override fun unZipFileSlowly(zipFilePath: String, extractLocationPath: String) {
        try {
            val fin = FileInputStream(zipFilePath)
            val zin = ZipInputStream(fin)
            var ze: ZipEntry?
            while (zin.nextEntry.also { ze = it } != null) {

                //create dir if required while unzipping
                if (ze!!.isDirectory) {
                    //TODO dirChecker(ze.getName());
                } else {
                    val fout = FileOutputStream(extractLocationPath + ze!!.name)
                    var c = zin.read()
                    while (c != -1) {
                        fout.write(c)
                        c = zin.read()
                    }
                    zin.closeEntry()
                    fout.close()
                }
            }
            zin.close()
        } catch (e: Exception) {
            println(e)
        }
    }

    /**
     * Zip helper function
     */
    private fun addFolderToZip(path: String, srcFolder: String, zip: ZipOutputStream) {
        val folder = File(srcFolder)
        if (folder.list() != null)
            for (fileName in folder.list()) {
                if (path == "") {
                    addFileToZip(folder.name, "$srcFolder/$fileName", zip)
                } else {
                    addFileToZip(path + "/" + folder.name, "$srcFolder/$fileName", zip)
                }
            }
    }

    /**
     * Zip helper function
     */
    private fun addFileToZip(path: String, srcFile: String, zip: ZipOutputStream) {
        val folder = File(srcFile)
        if (folder.isDirectory) {
            addFolderToZip(path, srcFile, zip)
        } else {
            val buf = ByteArray(1024)
            var len: Int
            val `in` = FileInputStream(srcFile)
            zip.putNextEntry(ZipEntry(path + "/" + folder.name))
            while (`in`.read(buf).also { len = it } > 0) {
                zip.write(buf, 0, len)
            }
        }
    }

    @RequiresApi(VERSION_CODES.N)
    override fun encryptFile(context: Context, srcFilePath: String, encryptedFileName: String): File? {
        var encryptedOutputFile: File? = null
        try {
            val inputStream: InputStream = FileInputStream(srcFilePath)

            /** Create Folder and file */
            createFolder("encrypt", srcFilePath)
            encryptedOutputFile = createFile(context, MEDIA_DIRECTORY, encryptedFileName, "enc")

            encryptToFile(
                keyStr = "keyLength16digit",
                specStr = "keySizeMustBe16-",
                inputStream,
                FileOutputStream(encryptedOutputFile)
            )
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return encryptedOutputFile
    }

    @RequiresApi(VERSION_CODES.N)
    override fun decryptFile(context: Context, encryptedFilePath: String, outputFileName: String): File? {
        var decryptedOutputFile: File? = null
        val mInputStream: InputStream = FileInputStream(encryptedFilePath)
        try {
            /** Create Folder and file */
            createFolder("decrypt", encryptedFilePath)
            decryptedOutputFile = createFile(context, MEDIA_DIRECTORY, outputFileName, null)

            decryptToFile(
                keyStr = "keyLength16digit",
                specStr = "keySizeMustBe16-",
                mInputStream,
                FileOutputStream(decryptedOutputFile)
            )
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return decryptedOutputFile
    }
}