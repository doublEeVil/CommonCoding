package com._22evil.test.date_time;

import org.junit.Test;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

public class TestTime {


    /**
     * 瞬时
     */
    @Test
    public void testInstant() {
        Instant instant = Instant.now();
        System.out.println(instant);
    }

    @Test
    public void testLocalTime() throws Exception{
        // 最基本用法
        LocalTime time = LocalTime.now();
        System.out.println(time);
        Thread.sleep(1200);
        System.out.println(time.getSecond()); //time不会变
        System.out.println(time.plusHours(1));// time改变

        // 构建自定义时间
        time = LocalTime.of(12,12,24);
        System.out.println(time);
    }

    @Test
    public void testLocalDate() {
        // 运行抛异常，因为不存在2018年13月36日
        // LocalDate date = LocalDate.of(2018, 13, 36);
        // System.out.println(date);

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.of(date, time);
        System.out.println(localDateTime);

    }

    @Test
    public void testLocalDateTime() {

    }


    @Test
    public void testZoneDateTime() {
        ZonedDateTime time = ZonedDateTime.now();
        System.out.println(time);
        System.out.println(time.getMonth().getValue()); //月份
        System.out.println(time.getYear());

        // 自定义
        time = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        System.out.println(time);

        // localDateTime 2 ZoneDateTime
        LocalDateTime local = LocalDateTime.now();
        ZonedDateTime zone = local.atZone(ZoneId.of("Europe/London"));
        System.out.println(local);
        System.out.println(zone);
    }

    /**
     * 打印所有时间地区
     * 600 多个
     * 真多。。。。
     */
    @Test
    public void testRegions() {
        ZoneId.getAvailableZoneIds().forEach(System.out::println);
    }

    @Test
    public void testMonths() {
        int days = Month.FEBRUARY.length(true); // 闰年
        System.out.println(days);
        System.out.println(Month.JANUARY);
        System.out.println(DayOfWeek.FRIDAY.getValue());

    }

    @Test
    public void testAdjusters() {
        LocalDateTime start = LocalDateTime.of(2018,7,8,21,23,16);
        LocalDateTime end = start.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println(start);
        System.out.println(end); // 调整到下个月第一天

        end = start.with(TemporalAdjusters.lastDayOfMonth());// 调整到本月最后一天
        System.out.println(end);
    }

    /**
     * 计算两个之间的时间间隔
     */
    public void testTemporalUnit() {

    }
}
