package com.demo.util;

import com.demo.exception.EncryptionException;
import com.demo.model.CipherSpec;
import com.demo.model.EncryptionProperties;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EncryptionUtil {

    public static String encrypt(String plaintext, EncryptionProperties encryptionProperties) throws Exception {
        validateField("plainText", plaintext);
        byte[] plainTextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedCipherBytes = doCipher(Cipher.ENCRYPT_MODE, plainTextBytes, encryptionProperties);
        return encodeToBase64(encryptedCipherBytes);
    }

    public static String decrypt(String cipherText, EncryptionProperties encryptionProperties) throws Exception {
        validateField("cipherText", cipherText);
        byte[] decodeCipherTextBytes = decodeFromBase64(cipherText);
        byte[] decryptedTextBytes = doCipher(Cipher.DECRYPT_MODE, decodeCipherTextBytes, encryptionProperties);

        return new String(decryptedTextBytes, StandardCharsets.UTF_8);
    }

    public static String encrypt(String plaintext, EncryptionProperties encryptionProperties, byte[] salt, byte[] iv) throws Exception {
        validateField("plainText", plaintext);
        byte[] plainTextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedCipherBytes = doCipher(Cipher.ENCRYPT_MODE, plainTextBytes, encryptionProperties.getCipherSpec(), encryptionProperties.getPassphrase(), salt, iv);
        return encodeToBase64(encryptedCipherBytes);
    }

    public static String decrypt(String plaintext, EncryptionProperties encryptionProperties, byte[] salt, byte[] iv) throws Exception {
        validateField("plainText", plaintext);
        byte[] plainTextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedCipherBytes = doCipher(Cipher.ENCRYPT_MODE, plainTextBytes, encryptionProperties.getCipherSpec(), encryptionProperties.getPassphrase(), salt, iv);
        return encodeToBase64(encryptedCipherBytes);
    }

    public static byte[] doCipher(int cipherMode, byte[] cipherOrplainTextBytes, EncryptionProperties encryptionProperties) throws Exception {
        SecretKeySpec keySpec = generateKeySpec(encryptionProperties);
        AlgorithmParameterSpec paramSpec = generateIvSpec(encryptionProperties.getIv(), encryptionProperties.getCipherSpec());
        return doCipher(cipherMode, cipherOrplainTextBytes, encryptionProperties.getCipherSpec().cipherTransformation, keySpec, paramSpec);
    }

    public static byte[] doCipher(int cipherMode, byte[] plainTextBytes, CipherSpec cipherSpec ,String passPhrase, byte[] saltByte, byte[] ivByte) throws Exception {
        SecretKeySpec keySpec = generateKeySpec(cipherSpec, passPhrase, saltByte);
        AlgorithmParameterSpec paramSpec = generateIvSpecFromByte(ivByte, cipherSpec);
        return doCipher(cipherMode, plainTextBytes, cipherSpec.cipherTransformation, keySpec, paramSpec);
    }

    public static byte[] doCipher(int cipherMode, byte[] cipherOrPlainText, String cipherAlgo, SecretKey key, AlgorithmParameterSpec ivSpec) throws Exception {
        validateField("cipherMode", cipherMode);
        validateField("cipherOrPlainText", cipherOrPlainText);
        validateField("cipherAlgo", cipherAlgo);
        validateField("key", key);
        validateField("ivSpec", ivSpec);
        Cipher cipher = Cipher.getInstance(cipherAlgo);
        cipher.init(cipherMode, key, ivSpec);
        return cipher.doFinal(cipherOrPlainText);
    }

    public static SecretKeySpec generateKeySpec(EncryptionProperties encryptionProperties) throws  Exception{
        validateField("salt", encryptionProperties.getSalt());
        byte[] saltBytes = decodeFromBase64(encryptionProperties.getSalt());
        return generateKeySpec(encryptionProperties.getCipherSpec(), encryptionProperties.getPassphrase(), saltBytes);
    }

    public static SecretKeySpec generateKeySpec(CipherSpec cipherSpec, String passPhrase, byte[] saltByte) throws  Exception{
        validateField("CipherSpec", cipherSpec);
        validateField("passPhrase", passPhrase);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(cipherSpec.secretKeyDerivationAlgorithm);
        KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), saltByte, cipherSpec.iterationCount, cipherSpec.keyLength);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), cipherSpec.algorithm);
    }

    public static AlgorithmParameterSpec generateIvSpec(String iv, CipherSpec cipherSpec) throws Exception{
        validateField("iv", iv);
        validateField("CipherSpec", cipherSpec);
        byte[] ivByte = decodeFromBase64(iv);
        return generateIvSpecFromByte(ivByte, cipherSpec);
    }

    public static AlgorithmParameterSpec generateIvSpecFromByte(byte[] ivByte, CipherSpec cipherSpec){
        return switch(cipherSpec){
            case AES_CBC_PKCS5Padding_128 , AES_CBC_PKCS5Padding_256 -> new IvParameterSpec(ivByte);
            case AES_GCM_NoPadding_128  , AES_GCM_NoPadding_256 -> new GCMParameterSpec(cipherSpec.tagLength, ivByte);
            default -> throw new IllegalArgumentException("Invalid Cipher Spec.."+ cipherSpec);
        };
    }

    public static void validateField(String fieldName, Object fieldValue){
        if(fieldValue == null || ((fieldValue instanceof String) && ((String)fieldValue).isEmpty())) {
            String errorMessage = "Field Value required";
            if(fieldName != null && !fieldName.isEmpty()) {
                errorMessage += "For the field [" + fieldName +"].";
            }
            throw new EncryptionException(errorMessage);
        }
        envVariableDefinition(String.valueOf(fieldValue));
    }

    private static void envVariableDefinition(String fieldValue){
        if(fieldValue.startsWith("${") && fieldValue.endsWith("}"))
            throw new IllegalArgumentException("Value Not defined.."+ fieldValue);
    }

    public static byte[] decodeFromBase64(String encodedString){
        return Base64.getDecoder().decode(encodedString);
    }

    public static String encodeToBase64(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }
}
