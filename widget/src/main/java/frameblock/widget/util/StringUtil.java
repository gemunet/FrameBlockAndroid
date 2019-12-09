package frameblock.widget.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm";

    public static boolean isEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static String dateToString(Date date, String format) {
        if(date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
        return "";
    }

    public static Date stringToDate(String value, String format) {
        try {
            return new SimpleDateFormat(format).parse(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
