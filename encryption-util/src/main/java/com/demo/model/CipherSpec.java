package com.demo.model;

import com.demo.util.EncryptionUtil;
import lombok.ToString;

import static com.demo.constants.CipherConstants.*;

@ToString
public enum CipherSpec {

    AES_CBC_PKCS5Padding_256(ALGORITHM, CBC_TRANSFORMATION, SECRET_KEY_ALGO, KEY_LENGTH_256, ITERATION_COUNT, SALE_LENGTH, IV_LENGTH, 0),
    AES_CBC_PKCS5Padding_128(ALGORITHM, CBC_TRANSFORMATION, SECRET_KEY_ALGO, KEY_LENGTH_128, ITERATION_COUNT, SALE_LENGTH, IV_LENGTH, 0),
    AES_GCM_NoPadding_256 (ALGORITHM, GCM_TRANFORMATION, SECRET_KEY_ALGO, KEY_LENGTH_256, ITERATION_COUNT, SALE_LENGTH, GCM_IV_LENGTH, GCM_TAG_LENGTH),
    AES_GCM_NoPadding_128(ALGORITHM, GCM_TRANFORMATION, SECRET_KEY_ALGO, KEY_LENGTH_128, ITERATION_COUNT, SALE_LENGTH, GCM_IV_LENGTH, GCM_TAG_LENGTH);

    public final String algorithm;

    public final String cipherTransformation;

    public final String secretKeyDerivationAlgorithm;

    public final int keyLength;

    public final int iterationCount;

    public final int saltLength;

    public final int ivLength;

    public final int tagLength;

    private CipherSpec(String algorithm, String cipherTransformation, String secretKeyDerivationAlgorithm, int keyLength, int iterationCount, int saltLength,
                       int ivLength, int tagLength) {
        EncryptionUtil.validateField("CipherSpec.algorithm", algorithm);
        EncryptionUtil.validateField("CipherSpec.cipherTransformation", cipherTransformation);
        EncryptionUtil.validateField("CipherSpec.secretKeyDerivationAlgorithm", secretKeyDerivationAlgorithm);
        EncryptionUtil.validateField("CipherSpec.keyLength", keyLength);
        EncryptionUtil.validateField("CipherSpec.iterationCount", iterationCount);
        EncryptionUtil.validateField("CipherSpec.saltLength", saltLength);
        EncryptionUtil.validateField("CipherSpec.ivLength", ivLength);
        EncryptionUtil.validateField("CipherSpec.tagLength", tagLength);

        this.algorithm = algorithm;
        this.cipherTransformation = cipherTransformation;
        this.secretKeyDerivationAlgorithm = secretKeyDerivationAlgorithm;
        this.keyLength = keyLength;
        this.iterationCount = iterationCount;
        this.saltLength = saltLength;
        this.ivLength = ivLength;
        this.tagLength = tagLength;
    }


}
