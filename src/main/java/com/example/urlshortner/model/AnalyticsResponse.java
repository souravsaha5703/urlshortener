package com.example.urlshortner.model;

import java.time.LocalDateTime;
import java.util.List;

public class AnalyticsResponse {
    private String shortCode;
    private String originalUrl;
    private LocalDateTime createdAt;
    private Long totalClicks;
    private List<ClickEventResponse> clicks;

    public AnalyticsResponse(String shortCode, String originalUrl, LocalDateTime createdAt, Long totalClicks, List<ClickEventResponse> clicks) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
        this.totalClicks = totalClicks;
        this.clicks = clicks;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(Long totalClicks) {
        this.totalClicks = totalClicks;
    }

    public List<ClickEventResponse> getClicks() {
        return clicks;
    }

    public void setClicks(List<ClickEventResponse> clicks) {
        this.clicks = clicks;
    }
}
