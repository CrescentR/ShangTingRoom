package com.room.ShangTingRoom.common.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Configuration
@EnableConfigurationProperties(MinioProperties.class)
@ConditionalOnProperty(name = "minio.endpoint")
public class MinoConfiguration {

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioClient getClient(){
        return MinioClient.builder().endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
}
