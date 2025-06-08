package com.demo.util;

import com.demo.service.EncryptionService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EncryptionServiceUtil {

    public static Map<String, EncryptionService> convertToMap(List<EncryptionService> encryptionServiceList){
        return encryptionServiceList.stream().collect(Collectors.toMap(EncryptionService::getBeanName, Function.identity()));

    }
}
