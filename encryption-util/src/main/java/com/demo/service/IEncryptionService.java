package com.demo.service;

import com.demo.model.CipherSpec;

public interface IEncryptionService {
    CipherSpec defaultCipherSpec = CipherSpec.AES_CBC_PKCS5Padding_256;

    String encrypt(String plaintext) throws Exception;

    String decrypt(String cipher) throws Exception;

    String encrypt(String plaintext, byte[] saltByte, byte[] ivByte) throws Exception;

    String decrypt(String plaintext, byte[] saltByte, byte[] ivByte) throws Exception;
}
