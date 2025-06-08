package com.demo.controller;

import com.demo.exception.EncryptionException;
import com.demo.service.EncryptionService;
import com.demo.util.EncryptionServiceUtil;
import com.demo.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cipher")
public class EncryptionController {

    private  final Map<String, EncryptionService> encryptionServiceMap;

    public EncryptionController(List<EncryptionService> encryptionService){
        if(encryptionService == null || encryptionService.isEmpty()){
            throw new EncryptionException("Error occured");
        }
        encryptionServiceMap = EncryptionServiceUtil.convertToMap(encryptionService);
    }

    public ResponseEntity<String> getEndpoints(){
        return ResponseEntity.ok("Endpoints:"+ encryptionServiceMap.keySet());
    }
}
