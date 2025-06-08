package com.demo.constants;

public class CipherConstants {
    public static final int KEY_LENGTH_256 = 256;
    public static final int ITERATION_COUNT = 65535;
    public static final int SALE_LENGTH = 16;
    public static final int IV_LENGTH = 16;
    public static final int GCM_IV_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 128;

    public static final String ALGORITHM = "AES";
    public static final String CBC_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final String GCM_TRANFORMATION = "AES/GCM/NoPadding";
    public static final String SECRET_KEY_ALGO = "PBKDF2WithHmacSHA256";

    public static final int KEY_LENGTH_128 = 128;


}
