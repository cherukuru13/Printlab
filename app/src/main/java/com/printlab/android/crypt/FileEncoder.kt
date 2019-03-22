package com.printlab.android.crypt

import java.security.GeneralSecurityException
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object FileEncoder {
    // Use this to derive the key from the password:
    private const val KEY_SIZE = 32

    @Throws(Exception::class)
    private fun generateKey(password: String): SecretKey {
        val keySpec = PBEKeySpec(password.toCharArray(), ByteArray(KEY_SIZE),
                100 /* iterationCount */, KEY_SIZE * 8 /* key size in bits */)
        try {
            val keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = keyFactory.generateSecret(keySpec).encoded
            return SecretKeySpec(keyBytes, "AES")
        } catch (e: Exception) {
            throw RuntimeException("Deal with exceptions properly!", e)
        }

    }


    @Throws(Exception::class)
    fun encodeByte(password: String, fileData: ByteArray): ByteArray {
        return encryptOrDecrypt(fileData, generateKey(password), true)
    }

    @Throws(Exception::class)
    fun decodeByte(password: String, fileData: ByteArray): ByteArray {
        return encryptOrDecrypt(fileData, generateKey(password), false)
    }

    private fun encryptOrDecrypt(
            data: ByteArray, key: SecretKey, isEncrypt: Boolean): ByteArray {
        try {
            val cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING")
            cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, key,
                    IvParameterSpec(retrieveIv()))
            return cipher.doFinal(data)
        } catch (e: GeneralSecurityException) {
            throw RuntimeException("This is unconceivable!", e)
        }

    }

    private fun retrieveIv(): ByteArray {

        val IV_SIZE = 16
        return ByteArray(IV_SIZE)
    }

}