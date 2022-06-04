package com.example.azure.mssql.config;

import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("azure")
public class AzureConfig {

    @Bean
    public ManagedIdentityCredential dbCredential() {
        ManagedIdentityCredential managedIdentityCredential =
                new ManagedIdentityCredentialBuilder()
                        .build();
        return  managedIdentityCredential;
    }
}
