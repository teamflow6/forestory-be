package com.teamflow.forestory_be.likes.domain.repository;

import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeCounterPort {
    void increase(TargetType type, Long targetId);
    void decrease(TargetType type, Long targetId);
}
