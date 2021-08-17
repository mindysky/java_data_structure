package com.hackrank.date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeTest {
    @Test
    public void testSimpleDateFormat() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Date date = new Date();

        String fmt = simpleDateFormat.format(date);
        System.out.println(fmt);


        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(simpleDateFormat1.format(date));
        String str = "2020-01-09";
        Date parse = simpleDateFormat1.parse(str);
        System.out.println(parse);
    }
}
