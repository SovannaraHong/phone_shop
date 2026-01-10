package com.phone_shop.phoneshop.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeneralTestTest {

    @Test
    void test() {
        List<String> list = List.of("bora");
        List<Integer> convert = GeneralTest.convert(list);
        assertEquals(1, convert.size());

    }


}