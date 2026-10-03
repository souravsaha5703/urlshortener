package com.example.urlshortner.controller;

import com.example.urlshortner.entity.ClickEvent;
import com.example.urlshortner.entity.Url;
import com.example.urlshortner.model.AnalyticsResponse;
import com.example.urlshortner.model.ClickEventResponse;
import com.example.urlshortner.model.UrlRequest;
import com.example.urlshortner.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/url")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<Url> createShortUrl(@Valid @RequestBody UrlRequest urlRequest){
        Url url = urlService.createShortUrl(urlRequest.getOriginalUrl());

        return new ResponseEntity<>(url,HttpStatus.CREATED);
    }

    @GetMapping("/{shortCode}/analytics")
    public ResponseEntity<AnalyticsResponse> getAnalytics(
            @PathVariable("shortCode") String shortCode){
        AnalyticsResponse analytics = urlService.getAnalytics(shortCode);
        return ResponseEntity.ok(analytics);
    }

}
