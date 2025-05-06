package com.teamflow.forestory_be.user.domain.policy;

import com.teamflow.forestory_be.user.domain.vo.Name;
import java.util.UUID;

public class RandomNameGenerator {

    private static final int RANDOM_LENGTH = 7;

    private RandomNameGenerator() {
    }

    public static Name generate() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String name = uuid.substring(0, RANDOM_LENGTH);
        return new Name(name);
    }
}
