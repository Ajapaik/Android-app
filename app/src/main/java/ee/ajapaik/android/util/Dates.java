package ee.ajapaik.android.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Dates {
    private static final String TAG = "Dates";
    private static final SimpleDateFormat ISO_8601 = new UTCDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ROOT);
    private static final SimpleDateFormat DDMMYYYY = new UTCDateFormat("dd-MM-yyyy", Locale.ROOT);
    private static final SimpleDateFormat YYYYMMDD_KKMMSS = new SimpleDateFormat("yyyy-MM-dd_kk-mm-ss");

    public static Date parse(String str) {
        if(str != null && str.endsWith("Z")) {
            try {
                return ISO_8601.parse(str);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Date parseDate(String date) {
        if (date == null) return null;
        try {
            return DDMMYYYY.parse(date);
        } catch (ParseException e) {
            Log.e(TAG, "Failed to parse date " + date);
            return null;
        }
    }

    public static String toFilename(Date date) {
        return (date != null) ? YYYYMMDD_KKMMSS.format(date) : null;
    }

    public static String toString(Date date) {
        return (date != null) ? ISO_8601.format(date) : null;
    }

    public static String toDDMMYYYYString(Date date) {
        return (date != null) ? DDMMYYYY.format(date) : null;
    }

    private static class UTCDateFormat extends SimpleDateFormat {
        static final long serialVersionUID = 1L;

        public UTCDateFormat(String format, Locale locale) {
            super(format, locale);
            setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    }
}
