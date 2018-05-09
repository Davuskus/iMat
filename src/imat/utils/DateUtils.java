package imat.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    /**
     * Returns a a formatted Date as a String. Some format examples: "yyyy/MM/dd" and "yyyy/MM/dd - HH:mm"
     *
     * @param format The date format.
     * @return The date in the given format.
     */
    public static String getFormattedDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

}
