package com.example.project3;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * A date class that builds the birthdate of the holder
 * @author Rory Xu, Hassan Alfareed
 */
public class Date implements Comparable<Date> {
    private final int year;
    private final int month;
    private final int day;

    /**
     * Construct a date object with month day and year
     *
     * @param date The date to be built
     */
    public Date(String date) {
        StringTokenizer st = new StringTokenizer(date);
        month = Integer.parseInt(st.nextToken("/")) - 1;
        day = Integer.parseInt(st.nextToken("/"));
        year = Integer.parseInt(st.nextToken("/"));

    }

    /**
     * Construct a date object with current real time month day and year
     */
    public Date() {
        Calendar present = Calendar.getInstance();
        this.month = present.get(Calendar.MONTH);
        this.day = present.get(Calendar.DATE);
        this.year = present.get(Calendar.YEAR);
    }

    /**
     * Compares one Date object to another in order to determine whether they are the same
     *
     * @param date The date to be compared with
     * @return 0 if they are the same
     * -1 if this patient comes before parameter,
     * 1 if this patient comes after parameter
     */
    @Override
    public int compareTo(Date date) {
        if (year != date.year) {
            return Integer.compare(year, date.year);
        }
        if (month != date.month) {
            return Integer.compare(month, date.month);
        }
        return Integer.compare(day, date.day);
    }

    /**
     * Converts the Date object into String format
     *
     * @return A String that contains this date
     */
    @Override
    public String toString() {
        return month+1 + "/" + day + "/" + year;
    }

    /**
     * Checks if the date is a valid date
     *
     * @return A boolean true if the date is valid, otherwise false
     */
    public boolean isValid() {

        int standardMonthDays = 30;
        int extendedMonthDays = 31;
        int nonLeapFebDays = 28;
        int leapFebDays = 29;

        if (year < 1900 || month < 0 || month > 11 || day < 1 || day > 31) return false;

        if (isThirty(this.month) && (this.day > standardMonthDays)) {
            return false;
        }

        if (month == Calendar.FEBRUARY && isLeapYr(this.year) && this.day > leapFebDays) {
            return false;
        }

        return month != Calendar.FEBRUARY || isLeapYr(this.year) || this.day <= nonLeapFebDays;
    }

    /**
     * A find method to check if value is inside array
     * @param arr an int array with given months
     * @param valueToCheck is the number/month to check
     * @return boolean value if the valueToCheck is inside the array
     */
    public static boolean inArray(int[] arr, int valueToCheck) {
        boolean result = false;
        for (int month : arr) {
            if (month == valueToCheck) {
                return true;
            }
        }
        return result;
    }

    /**
     * Checks if a year is leap or not
     * @param year given year
     * @return boolean value if the year is leap or not
     */
    public static boolean isLeapYr(int year) {
        final int QUADRENNIAL = 4;
        final int CENTENNIAL = 100;
        final int QUATERCENTENNIAL = 400;
        return ((year % QUADRENNIAL == 0) && (year % CENTENNIAL != 0)) || (year % QUATERCENTENNIAL == 0);
    }

    /**
     * Checks if a month is 31 days or not
     * @param month given a month
     * @return boolean value true if month is 31 days
     */
    public static boolean isThirtyOne(int month) {
        int[] months = {0, 2, 4, 6, 7, 9, 11};
        return (inArray(months, month));
    }

    /**
     * Checks if a month is 30 days or not
     * @param month given a month
     * @return boolean value true if month is 30 days
     */
    public static boolean isThirty(int month) {
        int[] months = {3, 5, 8, 10};
        return (inArray(months, month));
    }
}
