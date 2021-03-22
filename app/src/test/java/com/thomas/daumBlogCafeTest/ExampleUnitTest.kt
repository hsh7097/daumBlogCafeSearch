package com.thomas.daumBlogCafeTest

import com.thomas.daumBlogCafeTest.utils.TimeUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    /**
     * 문자열 (날짜) => SimpleDateFormat
     * 2021-02-13T05:19:25.000+09:00
     *
     * yyyy-MM-dd HH:mm:ss => yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm:ss.SSS => yyyy-MM-dd HH:mm:ss.SSS
     *
     * yyyy-MM-dd HH:mm:ssZ => yyyy-MM-dd HH:mm:ssX
     * yyyy-MM-dd HH:mm:ss+09 => yyyy-MM-dd HH:mm:ssX
     * yyyy-MM-dd HH:mm:ss+0900 => yyyy-MM-dd HH:mm:ssX
     * yyyy-MM-dd HH:mm:ss+09:00 => yyyy-MM-dd HH:mm:ssXXX
     * yyyy-MM-dd HH:mm:ssKST => yyyy-MM-dd HH:mm:ssZ
     *
     * yyyy-MM-dd HH:mm:ss.SSSZ => yyyy-MM-dd HH:mm:ss.SSSX
     * yyyy-MM-dd HH:mm:ss.SSS+09 => yyyy-MM-dd HH:mm:ss.SSSX
     * yyyy-MM-dd HH:mm:ss.SSS+0900 => yyyy-MM-dd HH:mm:ss.SSSX
     * yyyy-MM-dd HH:mm:ss.SSS+09:00 => yyyy-MM-dd HH:mm:ss.SSSXXX
     * yyyy-MM-dd HH:mm:ss.SSSKST => yyyy-MM-dd HH:mm:ss.SSSZ
     *
     * yyyy-MM-ddTHH:mm:ssZ => yyyy-MM-dd'T'HH:mm:ssX
     * yyyy-MM-ddTHH:mm:ss+09 => yyyy-MM-dd'T'HH:mm:ssX
     * yyyy-MM-ddTHH:mm:ss+0900 => yyyy-MM-dd'T'HH:mm:ssX
     * yyyy-MM-ddTHH:mm:ss+09:00 => yyyy-MM-dd'T'HH:mm:ssX
     * yyyy-MM-ddTHH:mm:ssKST => yyyy-MM-dd'T'HH:mm:ssZ
     *
     * yyyy-MM-ddTHH:mm:ss.SSSZ => yyyy-MM-dd'T'HH:mm:ss.SSSX
     * yyyy-MM-ddTHH:mm:ss.SSS+09 => yyyy-MM-dd'T'HH:mm:ss.SSSX
     * yyyy-MM-ddTHH:mm:ss.SSS+0900 => yyyy-MM-dd'T'HH:mm:ss.SSSX
     * yyyy-MM-ddTHH:mm:ss.SSS+09:00 => yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     * yyyy-MM-ddTHH:mm:ss.SSSKST => yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    @Test
    fun `parse date`() {


        val timeTest1 = "2021-03-14 08:40:15"
        val timeTest2 = "2021-03-14 08:40:15.000"
        val timeTest3 = "2021-03-14 08:40:15Z"
        val timeTest4 = "2021-03-14 08:40:15+09"
        val timeTest5 = "2021-03-14 08:40:15+0900"

        val timeTest6 = "2021-03-14 08:40:15+09:00"
        val timeTest7 = "2021-03-14 08:40:15KST"
        val timeTest8 = "2021-03-14 08:40:15.000Z"
        val timeTest9 = "2021-03-14 08:40:15.000+09"
        val timeTest10 = "2021-03-14 08:40:15.000+0900"

        val timeTest11 = "2021-03-14 08:40:15.000+09:00"
        val timeTest12 = "2021-03-14 08:40:15.000KST"
        val timeTest13 = "2021-03-14T08:40:15Z"
        val timeTest14 = "2021-03-14T08:40:15+09"
        val timeTest15 = "2021-03-14T08:40:15+0900"

        val timeTest16 = "2021-03-14T08:40:15+09:00"
        val timeTest17 = "2021-03-14T08:40:15KST"
        val timeTest18 = "2021-03-14T08:40:15.000Z"
        val timeTest19 = "2021-03-14T08:40:15.000+09"
        val timeTest20 = "2021-03-14T08:40:15.000+0900"

        val timeTest21 = "2021-03-14T08:40:15.000+09:00"
        val timeTest22 = "2021-03-14T08:40:15.000KST"


        // 시간 형식뒤에 Z 가 붙은 경우 그리니치 표준시 (+00 과 동일) 하여 9시간 추가

        assertEquals(TimeUtils.parseDate(timeTest1), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest2), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest3), Date(1615711215000))
        assertEquals(TimeUtils.parseDate(timeTest4), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest5), Date(1615678815000))

        assertEquals(TimeUtils.parseDate(timeTest6), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest7), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest8), Date(1615711215000))
        assertEquals(TimeUtils.parseDate(timeTest9), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest10), Date(1615678815000))

        assertEquals(TimeUtils.parseDate(timeTest11), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest12), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest13), Date(1615711215000))
        assertEquals(TimeUtils.parseDate(timeTest14), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest15), Date(1615678815000))

        assertEquals(TimeUtils.parseDate(timeTest16), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest17), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest18), Date(1615711215000))
        assertEquals(TimeUtils.parseDate(timeTest19), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest20), Date(1615678815000))

        assertEquals(TimeUtils.parseDate(timeTest21), Date(1615678815000))
        assertEquals(TimeUtils.parseDate(timeTest22), Date(1615678815000))

    }


    /**
     * 2021-02-13T05:19:25.000+09:00
     * 상위의 데이터를 파싱하여 해당 포멧으로 변경
     * format - 2019년 03월 14일 오전 11시 48분
     *
     */
    @Test
    fun `time convert time`() {
        val sourceString = "2021-02-13T05:19:25.000+09:00"
        val convertTime = TimeUtils.convertTime(sourceString)
        assertEquals(convertTime, "2021년 02월 13일 오전 05시 19분")
    }


    /**
     * 2021-02-13T05:19:25.000+09:00
     * 상위의 데이터를 파싱하여 해당 포멧으로 변경
     * format
     * 오늘
     * 어제
     * 그외 -> 2020년 06월 17일
     */
    @Test
    fun `parse date year month day`() {
        val testTodayDate = "2021-03-14T05:19:25.000+09:00"
        val testYesterdayDate = "2021-03-13T05:19:25.000+09:00"
        val testAnotherDayDate = "2021-03-12T05:19:25.000+09:00"

        val convertTodayTime = TimeUtils.convertTimeYearMonthDay(testTodayDate)
        val convertYesterdayTime = TimeUtils.convertTimeYearMonthDay(testYesterdayDate)
        val convertAnotherDayTime = TimeUtils.convertTimeYearMonthDay(testAnotherDayDate)

        assertEquals(convertTodayTime, "오늘")
        assertEquals(convertYesterdayTime, "어제")
        assertEquals(convertAnotherDayTime, "2021년 03월 12일")
    }

}