package com.teamflow.forestory_be.home.application.service.cache;

import com.teamflow.forestory_be.home.presentation.dto.PopularListItem;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class HomeWeeklyCache {

    private record Entry(List<PopularListItem> data, Instant expireAt) {}
    private final Map<String, Entry> store = new ConcurrentHashMap<>();

    public List<PopularListItem> get(String key, Instant now) {
        Entry e = store.get(key);
        if (e == null || now.isAfter(e.expireAt)) return null;
        return e.data;
    }

    public void put(String key, List<PopularListItem> value, Instant expireAt) {
        store.put(key, new Entry(value, expireAt));
    }
}
