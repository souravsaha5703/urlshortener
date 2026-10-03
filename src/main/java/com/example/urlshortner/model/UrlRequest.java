package com.example.urlshortner.model;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class UrlRequest {
    private String originalUrl;

    @NotBlank(message = "Original URL is required")
    @URL(message = "Invalid URL")
    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

}
