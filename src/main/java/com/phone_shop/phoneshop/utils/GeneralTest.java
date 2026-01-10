package com.phone_shop.phoneshop.utils;

import java.util.List;

public class GeneralTest {
    public static List<Integer> convert(List<String> list) {
        return list.stream().map(String::length).toList();
    }
}
