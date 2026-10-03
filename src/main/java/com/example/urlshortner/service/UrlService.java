package com.example.urlshortner.service;

import com.example.urlshortner.entity.ClickEvent;
import com.example.urlshortner.entity.Url;
import com.example.urlshortner.exception.UrlNotFoundException;
import com.example.urlshortner.model.AnalyticsResponse;
import com.example.urlshortner.model.ClickEventResponse;
import com.example.urlshortner.repository.ClickEventRepository;
import com.example.urlshortner.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final ClickEventRepository clickEventRepository;

    public UrlService(UrlRepository urlRepository,ClickEventRepository clickEventRepository){
        this.urlRepository = urlRepository;
        this.clickEventRepository = clickEventRepository;
    }

    public Url createShortUrl(String originalUrl){
        String shortCode = generateShortCode();

        Url url = new Url();

        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());
        url.setClickCount(0L);

        return urlRepository.save(url);
    }

    public Url getUrlByShortCode(String shortCode){
        return urlRepository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException("Short URL not found"));
    }

    public void recordClick(Url url,String userAgent, String referrer){
        ClickEvent clickEvent = new ClickEvent();

        clickEvent.setUrl(url);
        clickEvent.setUserAgent(userAgent);
        clickEvent.setReferrer(referrer);
        clickEvent.setClickedAt(LocalDateTime.now());

        clickEventRepository.save(clickEvent);
    }

    public void incrementClickCount(Url url){
        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);
    }

    public AnalyticsResponse getAnalytics(String shortCode){
        Url url = getUrlByShortCode(shortCode);
        List<ClickEvent> clickEvents = clickEventRepository.findByUrl(url);
        List<ClickEventResponse> clickDetails =  clickEvents.stream().map(click -> new ClickEventResponse(click.getClickedAt(),click.getUserAgent(),click.getReferrer())).toList();
        return new AnalyticsResponse(url.getShortCode(),url.getOriginalUrl(),url.getCreatedAt(),url.getClickCount(),clickDetails);
    }

    private String generateShortCode(){
        String shortCode;

        do {
            shortCode = UUID.randomUUID()
                    .toString()
                    .substring(0, 6);
        } while (urlRepository.existsByShortCode(shortCode));

        return shortCode;
    }
}
