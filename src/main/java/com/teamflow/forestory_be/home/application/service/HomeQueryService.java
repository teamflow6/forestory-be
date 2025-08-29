package com.teamflow.forestory_be.home.application.service;

import com.teamflow.forestory_be.article.domain.repository.ArticleRepositoryPort;
import com.teamflow.forestory_be.draft.domain.vo.DraftScope;
import com.teamflow.forestory_be.home.application.dto.query.GetTabCursorQuery;
import com.teamflow.forestory_be.home.application.dto.query.GetWeeklyTopByTypeQuery;
import com.teamflow.forestory_be.home.application.service.cache.HomeWeeklyCache;
import com.teamflow.forestory_be.home.presentation.dto.PopularCursorResponse;
import com.teamflow.forestory_be.home.presentation.dto.PopularListItem;
import com.teamflow.forestory_be.home.domain.vo.TabSort;
import com.teamflow.forestory_be.story.series.domain.repository.SeriesRepositoryPort;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HomeQueryService {

    private final ArticleRepositoryPort articleRepository;
    private final SeriesRepositoryPort seriesRepository;

    // 분리된 컴포넌트 주입
    private final WeeklyWindowProvider weeklyWindowProvider;
    private final HomeWeeklyCache homeWeeklyCache;

    private static final int HOME_CACHE_SIZE = 60; // 섹션 캐시 적재량(여유)

    /* ===== 홈 섹션: 주간 인기 TopN (캐시 사용) ===== */
    @Transactional(readOnly = true)
    public Map<String, List<PopularListItem>> getWeeklyTopByType(GetWeeklyTopByTypeQuery q) {
        int n = Math.max(1, q.limit());
        Map<String, List<PopularListItem>> res = new LinkedHashMap<>();
        res.put("NOVEL",   slice(loadWeeklyPopular(DraftScope.NOVEL),   0, n));
        res.put("ESSAY",   slice(loadWeeklyPopular(DraftScope.ESSAY),   0, n));
        res.put("ARTICLE", slice(loadWeeklyPopular(DraftScope.ARTICLE), 0, n));
        return res;
    }

    private List<PopularListItem> loadWeeklyPopular(DraftScope scope) {
        String key = "weekly:popular:" + scope.name();

        // 1) 캐시 조회
        List<PopularListItem> cached = homeWeeklyCache.get(key, Instant.now());
        if (cached != null) return cached;

        // 2) 미스 시 DB 조회
        var since = weeklyWindowProvider.windowStart();
        var until = weeklyWindowProvider.windowEnd();

        List<PopularListItem> fresh;
        if (scope == DraftScope.ARTICLE) {
            fresh = articleRepository.findTopByWeeklyLikesRaw(HOME_CACHE_SIZE, since, until)
                    .stream().map(r -> mapRow(scope, r)).collect(Collectors.toList());
        } else {
            Type type = (scope == DraftScope.NOVEL) ? Type.NOVEL : Type.ESSAY;
            fresh = seriesRepository.findTopByWeeklyLikesRaw(type, HOME_CACHE_SIZE, since, until)
                    .stream().map(r -> mapRow(scope, r)).collect(Collectors.toList());
        }

        // 3) 다음 컷오프까지 캐시
        homeWeeklyCache.put(key, fresh, weeklyWindowProvider.nextExpire());
        return fresh;
    }

    /* ===== 탭 목록: 커서 페이징(POPULAR | LATEST) ===== */
    @Transactional(readOnly = true)
    public PopularCursorResponse getTabListByCursor(GetTabCursorQuery q) {
        var since = weeklyWindowProvider.windowStart();
        var until = weeklyWindowProvider.windowEnd();

        List<Object[]> rows;
        if (q.sort() == TabSort.POPULAR) {
            if (q.scope() == DraftScope.ARTICLE) {
                rows = articleRepository.findWeeklyPopularAfterCursorById(q.size(), since, until, q.cursorId());
            } else {
                Type type = (q.scope() == DraftScope.NOVEL) ? Type.NOVEL : Type.ESSAY;
                rows = seriesRepository.findWeeklyPopularAfterCursorById(type, q.size(), since, until, q.cursorId());
            }
        } else { // LATEST
            if (q.scope() == DraftScope.ARTICLE) {
                rows = articleRepository.findLatestAfterCursorById(q.size(), since, until, q.cursorId());
            } else {
                Type type = (q.scope() == DraftScope.NOVEL) ? Type.NOVEL : Type.ESSAY;
                rows = seriesRepository.findLatestAfterCursorById(type, q.size(), since, until, q.cursorId());
            }
        }

        var items = rows.stream().map(r -> mapRow(q.scope(), r)).toList();
        return PopularCursorResponse.of(items, q.size());
    }

    /* ===== 공용 매핑 ===== */
    private PopularListItem mapRow(DraftScope scope, Object[] r) {
        Long id = ((Number) r[0]).longValue();
        String title = (String) r[1];
        String thumb = (String) r[2];
        long weeklyLikes = r[3] == null ? 0L : ((Number) r[3]).longValue();
        long score = weeklyLikes * 2L; // 조회수는 미구현 → 제외
        return new PopularListItem(scope, String.valueOf(id), title, thumb, weeklyLikes, score);
    }

    private static List<PopularListItem> slice(List<PopularListItem> all, int offset, int limit) {
        if (all.isEmpty() || offset >= all.size()) return Collections.emptyList();
        int to = Math.min(all.size(), offset + limit);
        return all.subList(offset, to);
    }
}
