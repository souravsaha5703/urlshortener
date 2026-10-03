package com.example.urlshortner.model;

import java.time.LocalDateTime;

public class ClickEventResponse {
    private LocalDateTime clickedAt;
    private String userAgent;
    private String referrer;

    public ClickEventResponse(LocalDateTime clickedAt, String userAgent, String referrer) {
        this.clickedAt = clickedAt;
        this.userAgent = userAgent;
        this.referrer = referrer;
    }

    public LocalDateTime getClickedAt() {
        return clickedAt;
    }

    public void setClickedAt(LocalDateTime clickedAt) {
        this.clickedAt = clickedAt;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }
}
