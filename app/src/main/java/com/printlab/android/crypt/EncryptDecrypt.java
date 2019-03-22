package com.printlab.android.crypt;


import com.printlab.android.constants.AppConstants;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Sakthivel A on 8/12/15.
 */
public class EncryptDecrypt {

    private IvParameterSpec ivspec;
    private SecretKeySpec keyspec;
    private Cipher cipher;

    public EncryptDecrypt() {

        String iv = "fedcba9876543210";
        ivspec = new IvParameterSpec(iv.getBytes());

        try {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static byte[] hexToBytes(String str) {

        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }

    public void SetSecretKey(String secretKey) {

        keyspec = new SecretKeySpec(secretKey.getBytes(), AppConstants.Encryption.AES_ALGORITHM);
    }

    public byte[] decrypt(String code) throws Exception {

        // Logg.v("decrypt ", "SetEncryptedUrlFromJsonResponse code " + code);

        if (code == null || code.length() == 0)
            throw new Exception("Empty string");

        byte[] decrypted;

        try {

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            decrypted = cipher.doFinal(hexToBytes(code));
            //Remove trailing zeroes
            if (decrypted.length > 0) {
                int trim = 0;
                for (int i = decrypted.length - 1; i >= 0; i--)
                    if (decrypted[i] == 0)
                        trim++;

                if (trim > 0) {

                    byte[] newArray = new byte[decrypted.length - trim];
                    System.arraycopy(decrypted, 0, newArray, 0, decrypted.length - trim);
                    decrypted = newArray;
                }
            }
        } catch (Exception e) {
            throw new Exception("[decrypt] " + e.getMessage());
        }
        return decrypted;
    }
}