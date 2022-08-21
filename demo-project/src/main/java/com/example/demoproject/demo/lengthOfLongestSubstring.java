package com.example.demoproject.demo;

public class lengthOfLongestSubstring {
    public int lengthOfLongestSubstringFunc(String s) {
        int[] last = new int[128];
        int n = s.length();

        int res = 0;
        int start = 0; // 窗口开始位置
        for(int i = 0; i < n; i++) {
            //将charAt（）中的参数值转为ASCII值
            int index = s.charAt(i);
            start = Math.max(start, last[index]);
            res   = Math.max(res, i - start + 1);
            last[index] = i+1;
        }

        return res;
    }
}
