package com.arnab.storage

import java.io.File

interface ZipManager {

    /**
     * **Zip Folders and Files**
     *
     * @param srcFolderPath String
     * @param destZipFilePath String
     * @return outputZipFile - File
     */
    fun zipFiles(srcFolderPath: String, destZipFilePath: String): File

    /**
     * **Zip only provide list of files**
     *
     * @param files arrayOfStrings
     * @param zipFileName String
     */
    fun zipListOfFiles(files: ArrayList<String>, zipFileName: String)

    /**
     * **Unzip Folders and files**
     *
     * @param zipFilePath String
     * @param extractLocationPath String
     */
    fun unZipFile(zipFilePath: String, extractLocationPath: String)

    /**
     * **Unzip Folders and files slowly**
     *
     * @param zipFilePath String
     * @param extractLocationPath String
     */
    fun unZipFileSlowly(zipFilePath: String, extractLocationPath: String)

}