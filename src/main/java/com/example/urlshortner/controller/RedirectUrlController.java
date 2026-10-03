package com.example.urlshortner.controller;

import com.example.urlshortner.entity.ClickEvent;
import com.example.urlshortner.entity.Url;
import com.example.urlshortner.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RedirectUrlController {
    private final UrlService urlService;

    public RedirectUrlController(UrlService urlService){
        this.urlService = urlService;
    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getUrl(
            @PathVariable("shortCode") String shortCode,
            @RequestHeader(value = "User-Agent",required = false) String userAgent,
            @RequestHeader(value = "Referer",required = false) String referrer){
        Url url = urlService.getUrlByShortCode(shortCode);
        urlService.incrementClickCount(url);
        urlService.recordClick(url,userAgent,referrer);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getOriginalUrl())).build();
    }

}
