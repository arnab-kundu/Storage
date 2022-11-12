package com.arnab.storage

import android.content.Context
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.CipherOutputStream
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

abstract class EncryptionManager {

    val READ_WRITE_BLOCK_BUFFER = 1024
    val ALGO_IMAGE_ENCRYPTOR = "AES/CBC/PKCS5Padding"
    val ALGO_SECRET_KEY = "AES"

    /**
     * **Encrypt File**
     *
     * @param context Context
     * @param srcFilePath String
     * @param encryptedFileName String
     * @return encryptedFile - File?
     */
    abstract fun encryptFile(context: Context, srcFilePath: String, encryptedFileName: String): File?

    /**
     * **Decrypt File**
     *
     * @param filePath String
     * @param rule String
     * @return decryptedFile - File
     */
    abstract fun decryptFile(context: Context, encryptedFilePath: String, outputFileName: String): File?

    /**
     * Encrypt file
     *
     * @param keyStr            Key is a String of length 16            (i.e. 128 bit)
     * @param specStr           SpecStr is also a String of length 16.  (i.e. 128 bit)
     * @param inputStream       InputStream of the file to be encrypted (Input File)
     * @param outputStream      OutputStream of the encrypted file.     (Output File)
     */
    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        IOException::class
    )
    protected fun encryptToFile(keyStr: String, specStr: String, inputStream: InputStream, outputStream: OutputStream) {
        var out = outputStream
        try {
            val iv = IvParameterSpec(specStr.toByteArray(charset("UTF-8")))
            val keySpec = SecretKeySpec(keyStr.toByteArray(charset("UTF-8")), ALGO_SECRET_KEY)
            val c = Cipher.getInstance(ALGO_IMAGE_ENCRYPTOR)
            c.init(Cipher.ENCRYPT_MODE, keySpec, iv)
            out = CipherOutputStream(out, c)
            var count = 0
            val buffer = ByteArray(READ_WRITE_BLOCK_BUFFER)
            while (inputStream.read(buffer).also { count = it } > 0) {
                out.write(buffer, 0, count)
            }
        } finally {
            out.close()
        }
    }


    /**
     * Decrypt file
     *
     * @param keyStr            Key is a String of length 16            (i.e. 128 bit)
     * @param specStr           SpecStr is also a String of length 16.  (i.e. 128 bit)
     * @param inputStream       InputStream of the file to be decrypted (Input File)
     * @param outputStream      OutputStream of the decrypted file.     (Output File)
     */
    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        IOException::class
    )
    protected fun decryptToFile(keyStr: String, specStr: String, inputStream: InputStream, outputStream: OutputStream) {
        var out = outputStream
        try {
            val iv = IvParameterSpec(specStr.toByteArray(charset("UTF-8")))
            val keySpec = SecretKeySpec(keyStr.toByteArray(charset("UTF-8")), ALGO_SECRET_KEY)
            val c = Cipher.getInstance(ALGO_IMAGE_ENCRYPTOR)
            c.init(Cipher.DECRYPT_MODE, keySpec, iv)
            out = CipherOutputStream(out, c)
            var count = 0
            val buffer = ByteArray(READ_WRITE_BLOCK_BUFFER)
            while (inputStream.read(buffer).also { count = it } > 0) {
                out.write(buffer, 0, count)
            }
        } finally {
            out.close()
        }
    }

}