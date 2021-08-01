package com.hackrank.map;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("ddd", 123);
        map.put("cc", 456);
        map.put("aa", 900);
        //自然排序
        System.out.println(map);

        TreeMap<String, Integer> map2 = new TreeMap(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
        map2.put("ddd", 123);
        map2.put("cc", 456);
        map2.put("aa", 900);

        System.out.println(map2);



    }
}
