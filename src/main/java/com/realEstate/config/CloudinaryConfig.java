package com.realEstate.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dh0kxwepu",
                "api_key", "697941487951364",
                "api_secret", "HTFlGWBFUSeaMoiL9Ft_VAyIMGo",
                "secure", true
        ));
    }
}