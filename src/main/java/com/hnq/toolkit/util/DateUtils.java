package com.hnq.toolkit.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Java8时间差API
 * 1.Period
 * 2.Duration
 * 3.ChronoUnit
 * @see <a href="https://www.jianshu.com/p/8a6c3c07d7d0">Java8时间差API<a/>
 *
 * @author henengqiang
 * @date 2018/10/9
 */
public class DateUtils {

    private DateUtils() {}

    public static String defaultPattern(@Nullable String pattern) {
        return pattern == null ? "yyyy-MM-dd HH:mm:ss" : pattern;
    }

    public static LocalDateTime toLocalDateTime(@Nonnull Date date) {
        return new LocalDateTime(date);
    }

    public static LocalDate toLocalDate(@Nonnull Date a) {
        return new LocalDate(a);
    }

    public static DateTime toDateTime(@Nonnull Date a) {
        return new DateTime(a);
    }

    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    public static LocalDateTime nowDatetime() {
        return LocalDateTime.now();
    }

    public static LocalDateTime withYesterday() {
        return nowDatetime().minusDays(1);
    }

    public static LocalDateTime withYesterday(@Nonnull Date date) {
        return toLocalDateTime(date).minusDays(1);
    }

    public static LocalDateTime withTomorrow() {
        return nowDatetime().plusDays(1);
    }

    public static LocalDateTime withTomorrow(@Nonnull Date date) {
        return toLocalDateTime(date).plusDays(1);
    }

    public static LocalDateTime withTime(@Nonnull LocalDateTime dateTime, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) {
        return dateTime.withTime(hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
    }

    public static LocalDateTime startTimeOfDay(@Nonnull LocalDateTime dateTime) {
        return withTime(dateTime, 0, 0, 0, 0);
    }

    public static LocalDateTime endTimeOfDay(@Nonnull LocalDateTime dateTime) {
        return withTime(dateTime, 23, 59, 59, 999);
    }

    public static Date asLocalDate(@Nonnull Date datetime) {
        return toLocalDate(datetime).toDate();
    }

    public static String nowDatetime(@Nullable String pattern) {
        return nowDatetime().toString(defaultPattern(pattern));
    }

    public static String now() {
        return nowDatetime(null);
    }

    public static String format(@Nullable Date date) {
        return format(date, null);
    }

    public static String format(@Nullable Date date, @Nullable String pattern) {
        return date == null ? "" : DateTimeFormat.forPattern(defaultPattern(pattern)).print(date.getTime());
    }

    public static String formatDate(@Nullable Date date) {
        return format(date, "yyyy-MM-dd");
    }

    public static String formatTime(@Nullable Date date) {
        return format(date, "HH:mm:ss");
    }

    public static String formatYm(@Nullable Date date) {
        return format(date, "yyyy-MM");
    }

    public static String formatMd(@Nullable Date date) {
        return format(date, "MM-dd");
    }

    public static String formatHm(@Nullable Date date) {
        return format(date, "HH:mm");
    }

    public static Date parse(@Nonnull String dateStr) {
        return parse(dateStr, (String)null);
    }

    public static Date parse(@Nullable String dateStr, @Nullable String pattern) {
        return StringUtils.isEmpty(dateStr) ? null : DateTimeFormat.forPattern(defaultPattern(pattern)).parseDateTime(dateStr).toDate();
    }

    public static Date parseDate(@Nonnull String dateStr) {
        return parse(dateStr, "yyyy-MM-dd");
    }

    public static Date parseLocal(@Nullable String dateStr, @Nullable String pattern) {
        return StringUtils.isEmpty(dateStr) ? null : DateTimeFormat.forPattern(defaultPattern(pattern)).parseLocalDateTime(dateStr).toDate();
    }

    public static Period getPeriod(@Nullable Date a, @Nullable Date b) {
        return (new Interval(defaultTime(a), defaultTime(b))).toPeriod();
    }

    public static long defaultTime(@Nullable Date date) {
        return date == null ? 0L : date.getTime();
    }

    public static Duration getDuration(@Nullable Date a, @Nullable Date b) {
        return (new Interval(defaultTime(a), defaultTime(b))).toDuration();
    }

    public static int natureDaysBetween(@Nonnull Date a, @Nonnull Date b) {
        return Days.daysBetween(toLocalDate(a), toLocalDate(b)).getDays();
    }

    public static int daysBetween(@Nonnull Date a, @Nonnull Date b) {
        return Days.daysBetween(toLocalDateTime(a), toLocalDateTime(b)).getDays();
    }

    public static int hoursBetween(@Nonnull Date a, @Nonnull Date b) {
        return Hours.hoursBetween(toLocalDateTime(a), toLocalDateTime(b)).getHours();
    }

    public static int minutesBetween(@Nonnull Date a, @Nonnull Date b) {
        return Minutes.minutesBetween(toLocalDateTime(a), toLocalDateTime(b)).getMinutes();
    }

    public static Date plusYears(@Nonnull Date date, int years) {
        return addToDate(date, years, DurationFieldType.years());
    }

    public static String plusYears(@Nonnull Date date, int years, @Nullable String pattern) {
        return addToString(date, years, DurationFieldType.years(), pattern);
    }

    public static Date minusYears(@Nonnull Date date, int years) {
        return addToDate(date, 0 - years, DurationFieldType.years());
    }

    public static String minusYears(@Nonnull Date date, int years, @Nullable String pattern) {
        return addToString(date, 0 - years, DurationFieldType.years(), pattern);
    }

    public static Date plusMonths(@Nonnull Date date, int months) {
        return addToDate(date, months, DurationFieldType.months());
    }

    public static String plusMonths(@Nonnull Date date, int months, @Nullable String pattern) {
        return addToString(date, months, DurationFieldType.months(), pattern);
    }

    public static Date minusMonths(@Nonnull Date date, int months) {
        return addToDate(date, 0 - months, DurationFieldType.months());
    }

    public static String minusMonths(@Nonnull Date date, int months, @Nullable String pattern) {
        return addToString(date, 0 - months, DurationFieldType.months(), pattern);
    }

    public static Date plusWeeks(@Nonnull Date date, int weeks) {
        return addToDate(date, weeks, DurationFieldType.weeks());
    }

    public static String plusWeeks(@Nonnull Date date, int weeks, @Nullable String pattern) {
        return addToString(date, weeks, DurationFieldType.weeks(), pattern);
    }

    public static Date minusWeeks(@Nonnull Date date, int weeks) {
        return addToDate(date, 0 - weeks, DurationFieldType.weeks());
    }

    public static String minusWeeks(@Nonnull Date date, int weeks, @Nullable String pattern) {
        return addToString(date, 0 - weeks, DurationFieldType.weeks(), pattern);
    }

    public static Date plusDays(@Nonnull Date date, int days) {
        return addToDate(date, days, DurationFieldType.days());
    }

    public static String plusDays(@Nonnull Date date, int days, @Nullable String pattern) {
        return addToString(date, days, DurationFieldType.days(), pattern);
    }

    public static Date minusDays(@Nonnull Date date, int days) {
        return addToDate(date, 0 - days, DurationFieldType.days());
    }

    public static String minusDays(@Nonnull Date date, int days, @Nullable String pattern) {
        return addToString(date, 0 - days, DurationFieldType.days(), pattern);
    }

    public static Date plusHours(@Nonnull Date date, int hours) {
        return addToDate(date, hours, DurationFieldType.hours());
    }

    public static String plusHours(@Nonnull Date date, int hours, @Nullable String pattern) {
        return addToString(date, hours, DurationFieldType.hours(), pattern);
    }

    public static Date minusHours(@Nonnull Date date, int hours) {
        return addToDate(date, 0 - hours, DurationFieldType.hours());
    }

    public static String minusHours(@Nonnull Date date, int hours, @Nullable String pattern) {
        return addToString(date, 0 - hours, DurationFieldType.hours(), pattern);
    }

    public static Date plusMinutes(@Nonnull Date date, int minutes) {
        return addToDate(date, minutes, DurationFieldType.minutes());
    }

    public static String plusMinutes(@Nonnull Date date, int minutes, @Nullable String pattern) {
        return addToString(date, minutes, DurationFieldType.minutes(), pattern);
    }

    public static Date minusMinutes(@Nonnull Date date, int minutes) {
        return addToDate(date, 0 - minutes, DurationFieldType.minutes());
    }

    public static String minusMinutes(@Nonnull Date date, int minutes, @Nullable String pattern) {
        return addToString(date, 0 - minutes, DurationFieldType.minutes(), pattern);
    }

    public static Date plusSeconds(@Nonnull Date date, int seconds) {
        return addToDate(date, seconds, DurationFieldType.seconds());
    }

    public static String plusSeconds(@Nonnull Date date, int seconds, @Nullable String pattern) {
        return addToString(date, seconds, DurationFieldType.seconds(), pattern);
    }

    public static Date minusSeconds(@Nonnull Date date, int seconds) {
        return addToDate(date, 0 - seconds, DurationFieldType.seconds());
    }

    public static String minusSeconds(@Nonnull Date date, int seconds, @Nullable String pattern) {
        return addToString(date, 0 - seconds, DurationFieldType.seconds(), pattern);
    }

    public static Date plusMillis(@Nonnull Date date, int millis) {
        return addToDate(date, millis, DurationFieldType.millis());
    }

    public static String plusMillis(@Nonnull Date date, int millis, @Nullable String pattern) {
        return addToString(date, millis, DurationFieldType.millis(), pattern);
    }

    public static Date minusMillis(@Nonnull Date date, int millis) {
        return addToDate(date, 0 - millis, DurationFieldType.millis());
    }

    public static String minusMillis(@Nonnull Date date, int millis, @Nullable String pattern) {
        return addToString(date, 0 - millis, DurationFieldType.millis(), pattern);
    }

    public static Date addToDate(@Nonnull Date date, int number, @Nonnull DurationFieldType fieldType) {
        return add(date, number, fieldType).toDate();
    }

    public static String addToString(@Nonnull Date date, int number, @Nonnull DurationFieldType fieldType, @Nullable String pattern) {
        return add(date, number, fieldType).toString(defaultPattern(pattern));
    }

    public static LocalDateTime add(@Nonnull Date date, int number, @Nonnull DurationFieldType fieldType) {
        return toLocalDateTime(date).withFieldAdded(fieldType, number);
    }

    public static Date getYesterday() {
        return nowDate().minusDays(1).toDate();
    }

    public static String getYesterdayString() {
        return nowDate().minusDays(1).toString("yyyy-MM-dd");
    }

    public static Date getTomorrow() {
        return nowDate().plusDays(1).toDate();
    }

    public static String getTomorrowString() {
        return nowDate().plusDays(1).toString("yyyy-MM-dd");
    }

    public static Date getFirstDayOfMonth(@Nonnull Date date) {
        return toLocalDateTime(date).dayOfMonth().withMinimumValue().toDate();
    }

    public static Date getFirstDateOfMonth(@Nonnull Date date) {
        return withStartTime(toLocalDateTime(date).dayOfMonth().withMinimumValue());
    }

    public static Date getLastDayOfMonth(@Nonnull Date date) {
        return toLocalDateTime(date).dayOfMonth().withMaximumValue().toDate();
    }

    public static Date getLastDateOfMonth(@Nonnull Date date) {
        return withStartTime(toLocalDateTime(date).dayOfMonth().withMaximumValue());
    }

    public static Date getDayOfMonth(@Nonnull Date date, int dayOfMonth) {
        return toLocalDateTime(date).withDayOfMonth(dayOfMonth).toDate();
    }

    public static Date getDateOfMonth(@Nonnull Date date, int dayOfMonth) {
        return withStartTime(toLocalDateTime(date).withDayOfMonth(dayOfMonth));
    }

    public static Date getFirstDayOfWeek(@Nonnull Date date) {
        return toLocalDateTime(date).dayOfWeek().withMinimumValue().toDate();
    }

    public static Date getFirstDateOfWeek(@Nonnull Date date) {
        return withStartTime(toLocalDateTime(date).dayOfWeek().withMinimumValue());
    }

    public static Date getLastDayOfWeek(@Nonnull Date date) {
        return toLocalDateTime(date).dayOfWeek().withMaximumValue().toDate();
    }

    public static Date getLastDateOfWeek(@Nonnull Date date) {
        return withStartTime(toLocalDateTime(date).dayOfWeek().withMaximumValue());
    }

    public static Date getDayOfWeek(@Nonnull Date date, int dayOfWeek) {
        return toLocalDateTime(date).withDayOfWeek(dayOfWeek).toDate();
    }

    public static Date getDateOfWeek(@Nonnull Date date, int dayOfWeek) {
        return withStartTime(toLocalDateTime(date).withDayOfWeek(dayOfWeek));
    }

    public static Date getTimeOfDay(@Nonnull Date date, int hourOfDay) {
        return getTimeOfDay(date, hourOfDay, 0);
    }

    public static Date getTimeOfDay(@Nonnull Date date, int hourOfDay, int minuteOfHour) {
        return getTimeOfDay(date, hourOfDay, minuteOfHour, 0);
    }

    public static Date getTimeOfDay(@Nonnull Date date, int hourOfDay, int minuteOfHour, int secondOfMinute) {
        return getTimeOfDay(date, hourOfDay, minuteOfHour, secondOfMinute, 0);
    }

    public static Date getTimeOfDay(@Nonnull Date date, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) {
        return withTime(toLocalDateTime(date), hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond).toDate();
    }

    public static Date getTimeOfWeek(@Nonnull Date date, int dayOfWeek, int hourOfDay) {
        return getTimeOfWeek(date, dayOfWeek, hourOfDay, 0);
    }

    public static Date getTimeOfWeek(@Nonnull Date date, int dayOfWeek, int hourOfDay, int minuteOfHour) {
        return getTimeOfWeek(date, dayOfWeek, hourOfDay, minuteOfHour, 0);
    }

    public static Date getTimeOfWeek(@Nonnull Date date, int dayOfWeek, int hourOfDay, int minuteOfHour, int secondOfMinute) {
        return getTimeOfWeek(date, dayOfWeek, hourOfDay, minuteOfHour, secondOfMinute, 0);
    }

    public static Date getTimeOfWeek(@Nonnull Date date, int dayOfWeek, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) {
        return withTime(toLocalDateTime(date).withDayOfWeek(dayOfWeek), hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond).toDate();
    }

    public static Date getTimeOfMonth(@Nonnull Date date, int dayOfMonth, int hourOfDay) {
        return getTimeOfMonth(date, dayOfMonth, hourOfDay, 0);
    }

    public static Date getTimeOfMonth(@Nonnull Date date, int dayOfMonth, int hourOfDay, int minuteOfHour) {
        return getTimeOfMonth(date, dayOfMonth, hourOfDay, minuteOfHour, 0);
    }

    public static Date getTimeOfMonth(@Nonnull Date date, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute) {
        return getTimeOfMonth(date, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, 0);
    }

    public static Date getTimeOfMonth(@Nonnull Date date, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) {
        return withTime(toLocalDateTime(date).withDayOfMonth(dayOfMonth), hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond).toDate();
    }

    public static Date getStartTimeInDayOfMonth(@Nonnull Date date, int dayOfMonth) {
        return withStartTime(toLocalDateTime(date).withDayOfMonth(dayOfMonth));
    }

    public static Date getEndTimeInDayOfMonth(@Nonnull Date date, int dayOfMonth) {
        return withEndTime(toLocalDateTime(date).withDayOfMonth(dayOfMonth));
    }

    public static Date getStartTimeInDayOfWeek(@Nonnull Date date, int dayOfWeek) {
        return withStartTime(toLocalDateTime(date).withDayOfWeek(dayOfWeek));
    }

    public static Date getEndTimeInDayOfWeek(@Nonnull Date date, int dayOfWeek) {
        return withEndTime(toLocalDateTime(date).withDayOfWeek(dayOfWeek));
    }

    public static Date getStartTimeOfDay(@Nonnull Date date) {
        return withStartTime(toLocalDateTime(date));
    }

    public static Date getStartTimeOfDayBefore(@Nonnull Date date, int daysBefore) {
        return withStartTime(toLocalDateTime(date).minusDays(daysBefore));
    }

    public static Date getStartTimeOfDayAfter(@Nonnull Date date, int daysAfter) {
        return withStartTime(toLocalDateTime(date).plusDays(daysAfter));
    }

    public static Date getEndTimeOfDay(@Nonnull Date date) {
        return withEndTime(toLocalDateTime(date));
    }

    public static Date getEndTimeOfDayBefore(@Nonnull Date date, int daysBefore) {
        return withEndTime(toLocalDateTime(date).minusDays(daysBefore));
    }

    public static Date getEndTimeOfDayAfter(@Nonnull Date date, int daysAfter) {
        return withEndTime(toLocalDateTime(date).plusDays(daysAfter));
    }

    public static Date getStartTimeOfYesterday() {
        return withStartTime(withYesterday());
    }

    public static Date getStartTimeOfYesterday(@Nonnull Date date) {
        return withStartTime(withYesterday(date));
    }

    public static Date getEndTimeOfYesterday() {
        return withEndTime(withYesterday());
    }

    public static Date getEndTimeOfYesterday(@Nonnull Date date) {
        return withEndTime(withYesterday(date));
    }

    public static Date getStartTimeOfToday() {
        return withStartTime(nowDatetime());
    }

    public static Date getStartTimeOfTodayBefore(int daysBefore) {
        return withStartTime(nowDatetime().minusDays(daysBefore));
    }

    public static Date getStartTimeOfTodayAfter(int daysAfter) {
        return withStartTime(nowDatetime().plusDays(daysAfter));
    }

    public static Date getEndTimeOfToday() {
        return withEndTime(nowDatetime());
    }

    public static Date getEndTimeOfTodayBefore(int daysBefore) {
        return withEndTime(nowDatetime().minusDays(daysBefore));
    }

    public static Date getEndTimeOfTodayAfter(int daysAfter) {
        return withEndTime(nowDatetime().plusDays(daysAfter));
    }

    public static Date getStartTimeOfTomorrow() {
        return withStartTime(withTomorrow());
    }

    public static Date getStartTimeOfTomorrow(@Nonnull Date date) {
        return withStartTime(withTomorrow(date));
    }

    public static Date getEndTimeOfTomorrow() {
        return withEndTime(withTomorrow());
    }

    public static Date getEndTimeOfTomorrow(@Nonnull Date date) {
        return withEndTime(withTomorrow(date));
    }

    public static Date getTimeOfTodayBefore(int daysBefore) {
        return nowDatetime().minusDays(daysBefore).toDate();
    }

    public static Date getTimeOfTodayAfter(int daysAfter) {
        return nowDatetime().plusDays(daysAfter).toDate();
    }

    public static Date yesterday() {
        return withYesterday().toDate();
    }

    public static Date yesterday(@Nonnull Date date) {
        return withYesterday(date).toDate();
    }

    public static String yesterdayString() {
        return withYesterday().toString("yyyy-MM-dd HH:mm:ss");
    }

    public static String yesterdayString(@Nonnull Date date) {
        return withYesterday(date).toString("yyyy-MM-dd HH:mm:ss");
    }

    public static Date tomorrow() {
        return withTomorrow().toDate();
    }

    public static Date tomorrow(@Nonnull Date date) {
        return withTomorrow(date).toDate();
    }

    public static String tomorrowString() {
        return withTomorrow().toString("yyyy-MM-dd HH:mm:ss");
    }

    public static String tomorrowString(@Nonnull Date date) {
        return withTomorrow(date).toString("yyyy-MM-dd HH:mm:ss");
    }

    private static Date withStartTime(@Nonnull LocalDateTime dateTime) {
        return startTimeOfDay(dateTime).toDate();
    }

    private static Date withEndTime(@Nonnull LocalDateTime dateTime) {
        return endTimeOfDay(dateTime).toDate();
    }

    @SuppressWarnings("unchecked")
    public static List<Date> listDatesBetweenDays(@Nonnull Date startDate, @Nonnull Date endDate) {
        LocalDate start = toLocalDate(startDate);
        LocalDate end = toLocalDate(endDate);
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("StartDate must be less than or equal to endDate!");
        } else {
            ArrayList list = new ArrayList();

            do {
                list.add(start.toDate());
                start = start.plusDays(1);
            } while(start.isBefore(end));

            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<String> listDateStringsBetweenDays(@Nonnull Date startDate, @Nonnull Date endDate) {
        LocalDate start = toLocalDate(startDate);
        LocalDate end = toLocalDate(endDate);
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("StartDate must be less than or equal to endDate!");
        } else {
            ArrayList list = new ArrayList();

            do {
                list.add(start.toString("yyyy-MM-dd"));
                start = start.plusDays(1);
            } while(start.isBefore(end));

            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Date> listDatesDuringDays(@Nonnull Date startDate, @Nonnull Date endDate) {
        LocalDate start = toLocalDate(startDate);
        LocalDate end = toLocalDate(endDate);
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("StartDate must be less than or equal to endDate!");
        } else {
            ArrayList list = new ArrayList();

            do {
                list.add(start.toDate());
                start = start.plusDays(1);
            } while(!start.isAfter(end));

            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<String> listDateStringsDuringDays(@Nonnull Date startDate, @Nonnull Date endDate) {
        LocalDate start = toLocalDate(startDate);
        LocalDate end = toLocalDate(endDate);
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("StartDate must be less than or equal to endDate!");
        } else {
            ArrayList list = new ArrayList();

            do {
                list.add(start.toString("yyyy-MM-dd"));
                start = start.plusDays(1);
            } while(!start.isAfter(end));

            return list;
        }
    }

    public static int year(@Nonnull Date date) {
        return toLocalDateTime(date).getYear();
    }

    public static int monthOfYear(@Nonnull Date date) {
        return toLocalDateTime(date).getMonthOfYear();
    }

    public static int weekyear(@Nonnull Date date) {
        return toLocalDateTime(date).getWeekyear();
    }

    public static int weekOfWeekyear(@Nonnull Date date) {
        return toLocalDateTime(date).getWeekOfWeekyear();
    }

    public static int dayOfYear(@Nonnull Date date) {
        return toLocalDateTime(date).getDayOfYear();
    }

    public static int dayOfMonth(@Nonnull Date date) {
        return toLocalDateTime(date).getDayOfMonth();
    }

    public static int dayOfWeek(@Nonnull Date date) {
        return toLocalDateTime(date).getDayOfWeek();
    }

    public static int hourOfDay(@Nonnull Date date) {
        return toLocalDateTime(date).getHourOfDay();
    }

    public static int minuteOfHour(@Nonnull Date date) {
        return toLocalDateTime(date).getMinuteOfHour();
    }

    public static int secondOfMinute(@Nonnull Date date) {
        return toLocalDateTime(date).getSecondOfMinute();
    }

    public static int millisOfSecond(@Nonnull Date date) {
        return toLocalDateTime(date).getMillisOfSecond();
    }

    public static int millisOfDay(@Nonnull Date date) {
        return toLocalDateTime(date).getMillisOfDay();
    }

    public static boolean isSameDay(@Nonnull Date date1, @Nonnull Date date2) {
        return toLocalDate(date1).isEqual(toLocalDate(date2));
    }

    public static String formatStartTimeOfDay(@Nonnull Date date, @Nonnull String pattern) {
        Date datetime = getStartTimeOfDay(date);
        return format(datetime, pattern);
    }

    public static String formatStartTimeOfDay(@Nonnull Date date) {
        Date datetime = getStartTimeOfDay(date);
        return format(datetime);
    }

    public static String formatEndTimeOfDay(@Nonnull Date date, @Nonnull String pattern) {
        Date datetime = getEndTimeOfDay(date);
        return format(datetime, pattern);
    }

    public static String formatEndTimeOfDay(@Nonnull Date date) {
        Date datetime = getEndTimeOfDay(date);
        return format(datetime);
    }

}
