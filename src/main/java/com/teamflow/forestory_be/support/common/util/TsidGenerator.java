package com.teamflow.forestory_be.support.common.util;

import com.github.f4b6a3.tsid.TsidFactory;

public final class TsidGenerator {

    private static TsidFactory factory;

    private TsidGenerator() {
    }

    public static void initialize(TsidFactory tsidFactory) {
        factory = tsidFactory;
    }

    public static Long generate() {
        if (factory == null) {
            throw new IllegalStateException("TsidGenerator does not exists");
        }
        return factory.create().toLong();
    }
}
