package com.teamflow.forestory_be.home.application.service;

import java.time.*;
import org.springframework.stereotype.Component;

@Component
public class WeeklyWindowProvider {

    private static final ZoneId ZONE = ZoneId.of("Asia/Seoul");

    public ZonedDateTime lastCutoff() {
        var now = ZonedDateTime.now(ZONE);
        var t = now.with(java.time.DayOfWeek.TUESDAY).withHour(5).withMinute(0).withSecond(0).withNano(0);
        if (now.isBefore(t)) t = t.minusWeeks(1);
        return t;
    }

    public LocalDateTime windowStart() { return lastCutoff().minusWeeks(1).toLocalDateTime(); }
    public LocalDateTime windowEnd()   { return lastCutoff().toLocalDateTime(); }
    public Instant nextExpire()        { return lastCutoff().plusWeeks(1).toInstant(); }
}
