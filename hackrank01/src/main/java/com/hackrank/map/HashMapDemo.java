package com.hackrank.map;

import java.util.*;

import static java.util.Map.*;

public class HashMapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("AA", 123);
        map.put("bb", 456);
        map.put("cc", 900);

        Set<String> set = map.keySet();
        for (String s : set) {
            System.out.println(s);
        }

        Collection<Integer> values = map.values();
        for (Object obj:values){
            System.out.println(obj);
        }

        Set<Map.Entry<String, Integer>> entrySet =map.entrySet();
        for (Object obj : entrySet) System.out.println(((Entry) obj).getKey());


       // System.out.println(map);


    }
}
