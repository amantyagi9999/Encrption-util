package com.demo.model;

import com.demo.util.EncryptionUtil;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class EncryptionProperties {

    private String passphrase;
    private String salt;
    private String iv;
    private CipherSpec cipherSpec;

    public EncryptionProperties clone(){
        return  EncryptionProperties.builder()
                .passphrase(this.passphrase)
                .salt(this.salt)
                .iv(this.iv)
                .cipherSpec(this.cipherSpec)
                .build();
    }

    public void validate(){
        List<String> errors = new ArrayList<>();
        if(this.passphrase == null || this.passphrase.trim().isEmpty()){
            errors.add("Passphrase is required");
        }
        if(this.salt == null || this.salt.trim().isEmpty()){
            errors.add("Salt is required");
        }
        if(this.iv == null || this.iv.trim().isEmpty()){
            errors.add("SalIVt is required");
        }
        if(this.cipherSpec == null){
            errors.add("cipherSpec is required");
        }
        if(!errors.isEmpty()){
            throw new IllegalArgumentException("EncryptionProperties validation failed.."+ String.join(",", errors));
        }
    }

    public void testEncryptionAndDecryption(){
        validate();
        String testText = "Test Encryption and Decryption for given EncryptionProperties";
        try{
            String encryptedText = EncryptionUtil.encrypt(testText, this);
            String decryptedText = EncryptionUtil.decrypt(encryptedText, this);
            if(!testText.equals(decryptedText)){
                throw new RuntimeException("Encryption and Decryption failed.."+testText+".."+ decryptedText);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
