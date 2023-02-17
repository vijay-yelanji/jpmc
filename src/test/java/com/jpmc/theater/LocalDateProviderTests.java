package com.jpmc.theater;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class LocalDateProviderTests {
    @Test
    void makeSureCurrentTime() {
        assertNotNull(LocalDateProvider.singleton().currentDate());
    }
}
