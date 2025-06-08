package com.demo.config;

import com.demo.model.EncryptionProperties;
import com.demo.service.EncryptionService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
public class DynamicEncryptionBeanRegistration implements BeanDefinitionRegistryPostProcessor, EnvironmentAware{

    public static final String ENC_PROPERTY_KEY = "cm.core.encryption.properties";

    public static final String ENC_SERVICE_BEAN_PREFIX = "encService";

    @Setter
    private Environment environment;
    private Map<String, EncryptionProperties> encryptionPropertiesMap;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Binder binder = Binder.get(this.environment);
        encryptionPropertiesMap = binder.bind(ENC_PROPERTY_KEY, Bindable.mapOf(String.class, EncryptionProperties.class)).orElse(Map.of());
        if(encryptionPropertiesMap ==null || encryptionPropertiesMap.isEmpty())
            throw new IllegalArgumentException("Encryption property not configured");

        encryptionPropertiesMap.forEach((key, encryptionProperties) -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(EncryptionService.class);
            String beanName = ENC_SERVICE_BEAN_PREFIX + key;
            builder.addConstructorArgValue(beanName);
            builder.addConstructorArgValue(encryptionProperties);
            registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
        });

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

}
