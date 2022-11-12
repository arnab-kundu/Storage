package com.arnab.storage

import android.content.Context
import java.io.File
import java.io.InputStream


interface FileManager {

    fun createFolder(folderName: String, path: String): Boolean

    fun createAppsInternalPrivateStoragePath(path: String): File?

    fun createSharedStoragePath(path: String): File?

    fun renameFolder(folderPath: String, newFolderName: String)

    /**
     * **Delete folder, subFolders and files**
     *
     * @param directory
     */
    fun deleteFolder(directory: File)

    /**
     *  **Creates a new file**
     *
     *  @param context
     *  @param fileLocationCategory
     *  @param fileName
     *  @param fileExtension
     *  @return File
     */
    fun createFile(context: Context, fileLocationCategory: FileLocationCategory, fileName: String, fileExtension: String?): File

    /**
     * **Copy a file from source path to destination path**
     *
     * @param sourcePath String
     * @param destinationPath String
     * @return isCopySuccessful - Boolean
     */
    fun copyFile(sourcePath: String, destinationPath: String): Boolean

    /**
     * **Delete file**
     *
     * @param sourceFilePath
     * @return isFileDeleteSuccessful
     */
    fun deleteFile(sourceFilePath: String): Boolean

    /**
     * **Move file**
     *
     * @param sourcePath String
     * @param destinationPath String
     */
    fun moveFile(sourcePath: String, destinationPath: String)

    /** **Write data to a empty file**
     *  - Writes inputStream to file
     *  - Writes data to file
     *
     * @param inputStream InputStream
     * @param file File
     */
    fun copyInputStreamToFile(inputStream: InputStream, file: File)

    /**
     *  **Rename file**
     *
     * @param context Context
     * @param existingFilePath String
     * @param newFileName String
     * @return renamedFile
     */
    fun renameFile(context: Context, existingFilePath: String, newFileName: String): File

}
