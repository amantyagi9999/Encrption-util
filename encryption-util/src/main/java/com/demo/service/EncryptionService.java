package com.demo.service;

import com.demo.model.CipherSpec;
import com.demo.model.EncryptionProperties;
import com.demo.util.EncryptionUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
public class EncryptionService implements  IEncryptionService{

    private final String beanName;
    private final EncryptionProperties encryptionProperties;
    private static final String DEFAULT_ENC_SER_BEAN_NAME = "encryptionService";

    public EncryptionService(String beanName, EncryptionProperties encryptionProperties) throws  Exception{
        this.beanName = StringUtils.isBlank(beanName) ? DEFAULT_ENC_SER_BEAN_NAME : beanName;
        EncryptionUtil.validateField("EncryptionProperties", encryptionProperties);
        CipherSpec cipherSpec = encryptionProperties.getCipherSpec();
        if(cipherSpec == null){
            encryptionProperties.setCipherSpec(defaultCipherSpec);
        }
        encryptionProperties.testEncryptionAndDecryption();
        this.encryptionProperties = encryptionProperties;
    }

    @Override
    public String encrypt(String plaintext) throws Exception {
        try {
            return EncryptionUtil.encrypt(plaintext, this.encryptionProperties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String cipher) throws Exception {
        try {
            return EncryptionUtil.decrypt(cipher, this.encryptionProperties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encrypt(String plaintext, byte[] saltByte, byte[] ivByte) throws Exception {
        try {
            return EncryptionUtil.encrypt(plaintext,this.encryptionProperties, saltByte, ivByte);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String cipher, byte[] saltByte, byte[] ivByte) throws Exception {
        try {
            return EncryptionUtil.decrypt(cipher,this.encryptionProperties, saltByte, ivByte);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
