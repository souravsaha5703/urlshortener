package com.example.urlshortner.repository;

import com.example.urlshortner.entity.ClickEvent;
import com.example.urlshortner.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClickEventRepository extends JpaRepository<ClickEvent,Long> {
    List<ClickEvent> findByUrl(Url url);
}
