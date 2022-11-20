package com.arnab.storage

enum class FileLocationCategory {

    /** **Internal App directories files are:**
     *  - Not visible for user in Physical Device or emulator
     *  - Only visible in Android Studio's - **Device File Explorer**
     *  - Located at Path: **data/data/com.package.name/cache/..**
     *  - Located at Path: **data/data/com.package.name/..**
     *  - Located at Path: **data/data/com.package.name/files/..**
     */
    CACHE_DIRECTORY,
    DATA_DIRECTORY,
    FILES_DIRECTORY,

    /** **External App directories files are:**
     *  - Visible for user in Physical Device or emulator
     *  - Visible in Android Studio's - **Device File Explorer** as well
     *  - Located at Path: **root/sdcard/Android/data/com.package.name/cache/..**
     *  - Located at Path: **root/sdcard/Android/data/com.package.name/files/..**
     *  - Located at Path: **root/sdcard/Android/media/com.package.name/..**
     *  - Located at Path: **root/sdcard/Android/obb/com.package.name/..**
     */
    EXTERNAL_CACHE_DIRECTORY,
    EXTERNAL_FILES_DIRECTORY,
    MEDIA_DIRECTORY,
    OBB_DIRECTORY,

    /** **External Public directories file:**
     * - Visible for user in Physical Device or emulator
     * - Visible in Android Studio's - **Device File Explorer** as well
     * - Located at Path: **root/Music/..**
     * - Located at Path: **root/Download/..**
     * - Located at Path: **root/Videos/..**
     * - Located at Path: **root/images/..**
     */
    DOWNLOADS_DIRECTORY,
    DOCUMENT_DIRECTORY,
    MUSIC_DIRECTORY,
    PICTURES_DIRECTORY,
    VIDEOS_DIRECTORY,
}